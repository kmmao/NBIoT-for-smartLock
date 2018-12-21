package com.routon.plsy;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import com.routon.plsy.model.Command;
import com.routon.plsy.model.Heartbeats;
import com.routon.plsy.service.HeartbeatsService;
import com.routon.plsy.service.NbCommandService;
import com.routon.plsy.util.Constant;
import com.routon.plsy.util.HttpPostCustom;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.routon.plsy.model.Register;
import com.routon.plsy.service.RegisterService;
import com.routon.plsy.util.ConnectFtp;
import com.routon.plsy.util.Input;
import com.routon.plsy.websocket.NBServerEndpoint;

import net.sf.json.JSONObject;

/**
 * author : wangxiwei
 */
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

	@Autowired
	private RegisterService registerService;

    @Autowired
    private NbCommandService nbCommandService;

    @Autowired
    private HeartbeatsService heartbeatsService;

    private static int mRecvTimeout = 30000; // ms

    private static int commandWaitTime = 30000; //ms

    private static ExecutorService exec = Executors.newCachedThreadPool();

    private static Map<String, Future<Boolean>> cmdThread = new HashMap<String, Future<Boolean>>();

    private static ArrayList<String> snList = new ArrayList<String>();

    private static Boolean GuanganSwitch = true;

    //临时
    private static Map<String,String> machineInfo = new HashMap<String,String>();

    private static Logger logger = LoggerFactory.getLogger(GreetingController.class);

    static{
        machineInfo.put("868744039490994","a09b433e-4613-45e3-9d7d-a7e87e9770bc");
        machineInfo.put("868744039491273","1bd07308-66e7-4b40-8e97-b92d9bff1abf");
        machineInfo.put("868744030688943","8ab1c637-882b-4e28-b089-4585c6289776");
        machineInfo.put("868744030688729","2a419ef5-e099-455c-a21a-ae389558aade");
        machineInfo.put("868744030688943","30d7e2b5-bab3-4444-bdd5-9d2f3158af44");
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    /**
     * 用于接收电信NB开放平台的指令数据回调
     * @param request
     * @return
     */
    @RequestMapping(value = "/NBIoT/callback")
    public @ResponseBody
    String NBIoTCallback(HttpServletRequest request){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str.trim());
            }
            br.close();
            String msgInfo = sb.toString();
            logger.info(msgInfo);
            NBServerEndpoint.broadcast(msgInfo);
            //转发给广安
            if(GuanganSwitch){
                logger.info("post url:" + Constant.Guanganurl);
                String result = HttpPostCustom.sendHttpPost(Constant.Guanganurl, sb.toString());
                if(result.equals("200")){
                    logger.info("消息转发广安成功");
                } else if(result.equals("500")){
                    logger.info("广安服务内部错误");
                } else if(result.equals("error")){
                    logger.error("消息转发广安失败");
                } else {
                    logger.info("消息转发广安失败");
                }
            }
            //处理接收的指令反馈
            JSONObject commandjson = JSONObject.fromObject(sb.toString());
            //设备上报心跳自动同步网络时间
            //如果是设备定时上报
            Boolean isLockHeartBeats = commandjson.containsKey("notifyType");
            if(isLockHeartBeats){
                String deviceId = commandjson.getString("deviceId");
                String notifyType = commandjson.getString("notifyType");
                if(notifyType.equals("deviceDataChanged")){
                    String serviceType = commandjson.getJSONObject("service").getString("serviceType");
                    if(serviceType.equals("LockStatus")){
                        try {
                            logger.info("开始同步设备"+ deviceId +"的网络时间.");
                            String result = nbCommandService.synTime(deviceId);
                            logger.info("返回结果:" + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error(deviceId + "同步网络时间失败.");
                        }
                    }
                }
            }
            //如果是锁的指令返回
            Boolean commandcallback = commandjson.containsKey("result");
            if(commandcallback){
                String deviceId = commandjson.getString("deviceId");
                String commandId = commandjson.getString("commandId");
                String resultCode = commandjson.getJSONObject("result").getString("resultCode");
                //查出当前的指令状态
                Command cmd = nbCommandService.selectInfoByCommandid(commandId, deviceId);
                if(cmd != null){
                    String cmdStatus = cmd.getCommandstate();
                    //如果当前指令已经为SUCCESSFUL了，则不用更新
                    if(!cmdStatus.equals("SUCCESSFUL")){
                        cmd.setDeviceid(deviceId);
                        cmd.setCommandid(commandId);
                        cmd.setCommandstate(resultCode);
                        logger.info("开始更新指令状态，commandid[" + commandId + "]，deviceid["+ deviceId +"]的状态更新为" + resultCode);
                        nbCommandService.update(cmd);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return "200 OK";
    }
    
    @RequestMapping(value = "/NBIoT/smartLock")
    public @ResponseBody
    JSONObject NBIoTFaceInfoUpload(HttpServletRequest request){
        logger.info("收到420推送消息了....");
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        JSONObject result = new JSONObject();
        result.put("result", 0);
        //CloseableHttpClient httpclient = null;
        try 
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str.trim());
            }
            br.close();

            logger.info(sb.toString());
            
            JSONObject a = JSONObject.fromObject(sb.toString());
            String command = a.getString("command");
            if(command.equals("FaceInfoUpload")) {
            logger.info(a.toString());
            Register register = new Register();
            String name = a.getString("name");
            register.setName(name);
            String id = a.getString("id");
            register.setId(id);
            register.setGender(a.getString("gender"));
            register.setIdcard_no(a.getString("idcard_no"));
            String idwlt = a.getString("idwlt");
            register.setNation(a.getString("nation"));
            register.setAddr(a.getString("addr"));
            register.setBirth(a.getString("birth"));
            register.setAgency(a.getString("agency"));
            register.setValid_date(a.getString("valid_date"));
            register.setCommand("FaceInfoUpload");
            register.setVersion(Integer.parseInt(a.getString("version")));
            //deviceid里面是机器识别码
            String machineCode = a.getString("device_id");
            String deviceid = machineInfo.get(machineCode);
            register.setDevice_id(deviceid);
            register.setTerm_id(a.getString("term_id"));
            register.setRole("0");
            JSONObject i = JSONObject.fromObject(a.getString("image"));
            Date date = new Date();
            String fileName = a.getString("id") + ".jpg";
            register.setUpdate_time(date);
            InputStream input1 = Input.BaseToInputStream(idwlt);
            ConnectFtp.uploadFile("172.16.42.134", 21, "134", "123456", "D:"+"\\"+"ftp134"+"\\"+"cardphoto", fileName, input1);
            register.setImage_url("ftp://134:123456@172.16.42.134/facephoto/"+fileName);
            String data = i.getString("data");
            InputStream input = Input.GenerateImage(data, fileName);

            Boolean success = ConnectFtp.uploadFile("172.16.42.134", 21, "134", "123456", "D:\\ftp134\\facephoto", fileName, input);
            if (success) {
                logger.info("上传成功！");
            }
            
            Register rs = registerService.selectById(a.getString("id"));
            //查重
            if(rs != null) {
            	//存在信息，修改
            	register.setFace_id(rs.getFace_id());
            	int u = registerService.updateById(register);
            	int r = -1;
            	if(u > 0) {
            		r = 0;
                    //发送注册信息到页面
                    NBServerEndpoint.broadcast("{\"notifyType\":\"uploadPersonInfo\",\"deviceId\":\""+ deviceid +"\",\"faceid\": \""+ rs.getFace_id() +"\"," +
                            "\"name\":\""+ name +"\", \"cardno\":\""+ id +"\"}");
                    logger.info("信息成功修改！");
            	} else {
                    logger.info("信息成功失败！");
            	}
            	result.put("result", r);
	            result.put("face_id", rs.getFace_id());
            } else {
            	//不存在信息，添加
	            int temp = registerService.insertRegister(register);
	            int r = -1;
	            if(temp > 0) {
	            	r = 0;
                    //发送注册信息到页面
                    NBServerEndpoint.broadcast("{\"notifyType\":\"uploadPersonInfo\",\"deviceId\":\""+ deviceid +"\",\"faceid\": \""+ register.getFace_id() +"\"," +
                            "\"name\":\""+ name +"\", \"cardno\":\""+ id +"\"}");
                    logger.info("入库成功...");
	            } else {
                    logger.info("入库失败...");
	            }
            
	            result.put("result", r);
	            result.put("face_id", register.getFace_id());
              }
              //终端上报白名单、取消白名单，每次终端上报都是白名单的全集
            } else if(command.equals("UploadWhitelist")){
                Set<Long> needDelSet = new HashSet<Long>();
                String machineCode = a.getString("device_id");
                String deviceid = machineInfo.get(machineCode);
                String sn = a.getString("sn");
                synchronized(this){
                    snList.add(sn);
                }
                //注意，这个里面的facedid还包括删除的，要区分类型
                JSONArray faceidArray = a.getJSONArray("faceid_list");
                //将终端下发的白名单列表转换为Set集合
                Set<Long> newSet = new HashSet<Long>();
                for(int i = 0; i < faceidArray.size(); i++){
                    JSONObject o = faceidArray.getJSONObject(i);
                    newSet.add(Long.valueOf(o.getInt("face_id")));
                }
                //查出原先的白名单列表
                Heartbeats heartbeats = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                if(heartbeats != null){
                    Set<Long> orginalSet = new HashSet<Long>();
                    String wl = heartbeats.getWhitefacelist();
                    if(wl != null && !wl.equals("")){
                        String[] originalArr = wl.split(",");
                        for(String arr : originalArr){
                            orginalSet.add(Long.valueOf(arr));
                        }
                    }
                    //现在orginalSet里面为需要删除的白名单列表,并将其放入needDelSet中
                    orginalSet.removeAll(newSet);
                    needDelSet.addAll(orginalSet);
                }
                //需要删除的白名单列表
                List<Long> delList = new ArrayList<Long>();
                Iterator<Long> its = needDelSet.iterator();
                while(its.hasNext()){
                    delList.add(its.next());
                }
                //启动线程处理下发和删除的白名单
                cmdThread.put(sn, exec.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        logger.info("开启添加/删除白名单线程...");
                        //是否有指令失败
                        Boolean flag = false;
                        for(int i = 0; i < delList.size(); i++){
                            logger.info("开始删除白名单...");
                            long faceid = delList.get(i);
                            //通过faceid和deviceid唯一确定一台终端上的白名单
                            Register re = registerService.selectByfaceidAndDeviceid((int)faceid, deviceid);
                            if(re != null){
                                String personName = re.getName();
                                logger.info("开始发送第" + (i+1) + "条消息(删除)");
                                //删除白名单
                                String response = null;
                                try {
                                    response = nbCommandService.delWhiteNames("0", String.valueOf(faceid), deviceid);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(response != null){
                                    //指令开始时间
                                    long begintime = System.currentTimeMillis();
                                    JSONObject responseJson = JSONObject.fromObject(response);
                                    String commandId = responseJson.getString("commandId");
                                    String deviceId = responseJson.getString("deviceId");
                                    String status = responseJson.getString("status");
                                    if(status.equals("SENT")){
                                        JSONObject JO = responseJson.getJSONObject("command").getJSONObject("paras");
                                        String commandstr = JO.getString("CommandStr");
                                        Command com = new Command();
                                        com.setCommandid(commandId);
                                        com.setDeviceid(deviceId);
                                        com.setFace_id(faceid);
                                        com.setCommandtime(new Date());
                                        com.setCommandtype(2);  //删除白名单
                                        com.setCommandstate("SENT");  //平台已下发，等待设备消息回应
                                        com.setCommandstr(commandstr);
                                        com.setName(personName);
                                        com.setSn(sn);
                                        //指令存档
                                        nbCommandService.insertCommand(com);
                                        //等待查询指令返回状态
                                        Thread.sleep(3000);
                                        while(!flag){
                                            Command cmd = nbCommandService.selectInfoByCommandid(commandId, deviceId);
                                            String cmdTag = cmd.getCommandstate();
                                            if(cmdTag.equals("SUCCESSFUL")){
                                                //如果指令返回成功，则继续下面的指令
                                                logger.info("第" + (i+1) + "条白名单删除成功");
                                                break;
                                            } else if(cmdTag.equals("SENT") || cmdTag.equals("DELIVERED")){
                                                //指令当前还有未返回的，继续等待
                                                long curTime = System.currentTimeMillis();
                                                //超过时间则不再等待
                                                if(curTime - begintime > commandWaitTime){
                                                    logger.info("第" + (i+1) + "条白名单删除超时");
                                                    flag = true;
                                                }
                                            } else if(cmdTag.equals("FAILED") || cmdTag.equals("TIMEOUT")){
                                                //指令操作失败
                                                logger.info("第" + (i+1) + "条白名单删除失败");
                                                flag = true;
                                            }
                                        }
                                        //后面的指令也不再执行
                                        if(flag){
                                            break;
                                        }
                                    } else{
                                        logger.error("faceid:"+faceid + ",deviceid:"+ deviceid +" 白名单删除返回状态异常！");
                                        flag = true;
                                        break;
                                    }
                                } else{
                                    logger.error("faceid:"+faceid + ",deviceid:"+ deviceid +"白名单删除返回状态异常！");
                                    flag = true;
                                    break;
                                }
                            } else{
                                logger.error("faceid:"+faceid + "未在机器["+ deviceid + "]上注册");
                                flag = true;
                                break;
                            }
                        }

                        //发送白名单，判断之前是否有指令没有执行成功
                        if(!flag){
                            for(int i = 0; i < faceidArray.size(); i++){
                                logger.info("开始下发白名单...");
                                int faceid = faceidArray.getJSONObject(i).getInt("face_id");
                                //通过faceid和deviceid唯一确定一台终端上的白名单
                                Register re = registerService.selectByfaceidAndDeviceid(faceid, deviceid);
                                if(re != null){
                                    String cardid = re.getIdcard_no();
                                    String personName = re.getName();
                                    //下发白名单
                                    logger.info("开始发送第" + (i+1) + "条消息");
                                    String response = null;
                                    try {
                                        response = nbCommandService.sendWhiteNames(deviceid, faceid, "1", cardid, "", "", 0);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if(response != null){
                                        //指令开始时间
                                        long begintime = System.currentTimeMillis();
                                        JSONObject responseJson = JSONObject.fromObject(response);
                                        String commandId = responseJson.getString("commandId");
                                        String deviceId = responseJson.getString("deviceId");
                                        String status = responseJson.getString("status");
                                        if(status.equals("SENT")){
                                            JSONObject JO = responseJson.getJSONObject("command").getJSONObject("paras");
                                            String commandstr = JO.getString("CommandStr");
                                            Command com = new Command();
                                            com.setCommandid(commandId);
                                            com.setDeviceid(deviceId);
                                            com.setFace_id(faceid);
                                            com.setCommandtime(new Date());
                                            com.setCommandtype(1);  //白名单
                                            com.setCommandstate("SENT");  //平台已下发，等待设备消息回应
                                            com.setCommandstr(commandstr);
                                            com.setName(personName);
                                            com.setSn(sn);
                                            //指令存档
                                            nbCommandService.insertCommand(com);
                                            //等待查询指令返回状态
                                            Thread.sleep(3000);
                                            while (!flag){
                                                Command cmd = nbCommandService.selectInfoByCommandid(commandId, deviceId);
                                                String cmdTag = cmd.getCommandstate();
                                                if(cmdTag.equals("SUCCESSFUL")){
                                                    //如果指令返回成功，则继续下面的指令
                                                    logger.info("第" + (i+1) + "条白名单下发成功");
                                                    break;
                                                } else if(cmdTag.equals("SENT") || cmdTag.equals("DELIVERED")){
                                                    //指令当前还有未返回的，继续等待
                                                    long curTime = System.currentTimeMillis();
                                                    //超过时间则不再等待
                                                    if(curTime - begintime > commandWaitTime){
                                                        logger.info("第" + (i+1) + "条白名单下发超时");
                                                        flag = true;
                                                    }
                                                } else if(cmdTag.equals("FAILED") || cmdTag.equals("TIMEOUT")){
                                                    //指令操作失败，后面的指令不再执行
                                                    logger.info("第" + (i+1) + "条白名单下发失败");
                                                    flag = true;
                                                }
                                            }
                                            if(flag){
                                                break;
                                            }
                                        } else{
                                            logger.error("faceid:"+faceid + ",deviceid:"+ deviceid +"白名单下发返回状态异常！");
                                            flag = true;
                                            break;
                                        }
                                    } else{
                                        logger.error("faceid:"+faceid + ",deviceid:"+ deviceid +"白名单下发返回状态异常！");
                                        flag = true;
                                        break;
                                    }
                                } else{
                                    logger.error("faceid:"+faceid + "未在机器["+ deviceid + "]上注册");
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        return flag;
                    }
                }));
                result.put("sn", sn);
                result.put("result", 0);
                logger.info("终端上报结束...");
            }else if(command.equals("HeartBeats")){
                String machineCode = a.getString("device_id");
                String deviceid = machineInfo.get(machineCode);
                //查出平台新下发的白名单（不包括终端下发的白名单），此时只包括下发了的，并不知道指令的执行状态
                List<Command> whiteList = nbCommandService.selectInfoByreport(1, deviceid, 1);
                //查出平台新删除的白名单
                List<Command> delWhiteList = nbCommandService.selectInfoByreport(1, deviceid, 2);
                //查出平台更新的管理员，里面包含新下发的和删除的
                List<Command> managerList = nbCommandService.selectInfoByManagerReport(1, deviceid);
                //终端下发的白名单列表
                if(snList.size() != 0){
                    for(int i = 0; i < snList.size(); i++){
                        JSONArray jos = new JSONArray();
                        JSONObject jo = new JSONObject();
                        String curSn = snList.get(i);
                        if(cmdThread.containsKey(curSn)){
                            //如果线程还没有返回，则这里会阻塞
                            Boolean flag = cmdThread.get(curSn).get();
                            //flag为false则表示指令全部执行成功
                            if(flag == false){
                                List<Command> cmdList = nbCommandService.selectStateBySn(curSn, deviceid);
                                Set<Long> sentFaceid = new HashSet<Long>();
                                Set<Long> delFaceid = new HashSet<Long>();
                                for(int j = 0; j < cmdList.size(); j++){
                                    Command cmd = cmdList.get(j);
                                    int cmdType = (int) cmd.getCommandtype();
                                    long faceid = cmdList.get(j).getFace_id();
                                    if(cmdType == 1){
                                        sentFaceid.add(faceid);
                                    } else if(cmdType == 2){
                                        delFaceid.add(faceid);
                                    }
                                }
                                //将终端下发成功的白名单更新到heartbeats中
                                Heartbeats heartbeats = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                                if(heartbeats != null){
                                    String wl = heartbeats.getWhitefacelist();
                                    Set<Long> originalSet = new HashSet<Long>();
                                    if(wl != null && !wl.equals("")){
                                        String[] originalArr = wl.split(",");
                                        for(String arr : originalArr){
                                            originalSet.add(Long.valueOf(arr));
                                        }
                                    }
                                    //删除已经下发成功白名单的列表,添加新增的
                                    originalSet.removeAll(delFaceid);
                                    originalSet.addAll(sentFaceid);
                                    StringBuilder updatedWhiteList = new StringBuilder("");
                                    Iterator<Long> its = originalSet.iterator();
                                    while(its.hasNext()){
                                        long faceid = its.next();
                                        if(updatedWhiteList.toString().equals("")){
                                            updatedWhiteList.append(faceid);
                                        } else{
                                            updatedWhiteList.append(",").append(faceid);
                                        }
                                    }
                                    heartbeats.setWhitefacelist(updatedWhiteList.toString());
                                    heartbeats.setIsreportwhite(1);
                                    heartbeats.setUpdate_time(new Date());
                                    heartbeatsService.updateHeartbeatsByDeviceid(heartbeats);

                                }
                                //终端处理成功
                                jo.put("state", 0);
                                jo.put("sn", curSn);
                                jos.add(jo);
                                //移除SN
                                snList.remove(curSn);
                            } else{
                                //终端处理失败，直接返回，不作任何处理
                                jo.put("state", 1);
                                jo.put("sn", curSn);
                                jos.add(jo);
                                //移除SN,终端失败后也不再处理
                                snList.remove(curSn);
                            }
                        }
                        result.put("report_whitelist_ack", jos);
                        result.put("result", 0);
                    }
                }
                //平台下发白名单列表
                if(whiteList.size() != 0){
                    //最新下发白名单的faceid集合
                    Set<Long> newFaceid_set = new TreeSet<Long>();
                    //需要更新的白名单faceid集合
                    Set<Long> updatedFaceid_set = new TreeSet<Long>();
                    //结果是否需要上报标识
                    Boolean flag = false;
                    for(int i = 0; i < whiteList.size(); i++){
                        Command cmd = whiteList.get(i);
                        String commandstate = cmd.getCommandstate();
                        long cmdTime = cmd.getCommandtime().getTime();
                        long faceid = cmd.getFace_id();
                        if(commandstate.equals("SUCCESSFUL")){
                            //加入到心跳返回JSON，并去除重复faceid,这个里面是新下发白名单的faceid
                            newFaceid_set.add(faceid);
                            //更新结果已经返回成功的指令，结果返回成功的指令将其标志置为0，下次不再筛选
                            cmd.setReport_white(0);
                            nbCommandService.updateReportByDeviceid(cmd);
                            //除了SUCCESSFUL、TIMEOUT和FAILED外，还有SENT和DELIVERED这两种状态不作任何处理，其标志位也不置为0，等待下次
                            //心跳过来的时候再判断一次其状态。
                        } else if(commandstate.equals("TIMEOUT") || commandstate.equals("FAILED")){
                            flag = true;
                            //如果失败或者超时，那下次也不再筛选，将command中的report_white置为0,但是并不加到心跳返回JSON
                            cmd.setReport_white(0);
                            nbCommandService.updateReportByDeviceid(cmd);
                        } else if(commandstate.equals("DELIVERED") || commandstate.equals("SENT")){
                            flag = true;
                            //如果指令长时间还是这两种状态，那么也不必总是上报，防止终端不停报授权已更新
                            //DELIVERED和SENT维持15s则下次不再筛选
                            if(System.currentTimeMillis() - cmdTime >= mRecvTimeout){
                                flag = false;
                                cmd.setReport_white(0);
                                nbCommandService.updateReportByDeviceid(cmd);
                            }
                        }
                    }
                    //加入到Heatbeats中，以让终端获取
                    Heartbeats heartbeats = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                    if(heartbeats != null){
                        String wl = heartbeats.getWhitefacelist();
                        if(wl != null && !wl.equals("")){
                            String[] originalArr = wl.split(",");
                            for(String arr : originalArr){
                                updatedFaceid_set.add(Long.valueOf(arr));
                            }
                        }
                        //将最新下发的faceid集合融合到原来的集合中
                        updatedFaceid_set.addAll(newFaceid_set);
                        //遍历集合插入到heartbeats中
                        StringBuilder updatedWhiteList = new StringBuilder("");
                        Iterator<Long> its = updatedFaceid_set.iterator();
                        while(its.hasNext()){
                            long faceid = its.next();
                            if(updatedWhiteList.toString().equals("")){
                                updatedWhiteList.append(faceid);
                            }else{
                                updatedWhiteList.append(",").append(faceid);
                            }
                        }
                        //更新到对应deviceid的白名单列表
                        heartbeats.setWhitefacelist(updatedWhiteList.toString());
                        //平台下发的指令需要置为0，表示还未上报
                        heartbeats.setIsreportwhite(0);
                        heartbeats.setUpdate_time(new Date());
                        heartbeatsService.updateHeartbeatsByDeviceid(heartbeats);
                    } else {
                        //如果heatbeats中不存在对应deviceid的心跳记录，则直接把新增的faceid插入到白名单列表中
                        StringBuilder updatedWhiteList = new StringBuilder("");
                        //将最新下发的faceid集合融合到原来的集合中
                        updatedFaceid_set.addAll(newFaceid_set);
                        Iterator<Long> its = newFaceid_set.iterator();
                        while(its.hasNext()){
                            long faceid = its.next();
                            if(updatedWhiteList.toString().equals("")){
                                updatedWhiteList.append(faceid);
                            }else{
                                updatedWhiteList.append(",").append(faceid);
                            }
                        }
                        Heartbeats h = new Heartbeats();
                        h.setDevice_id(deviceid);
                        h.setWhitefacelist(updatedWhiteList.toString());
                        //1-已上报 0-未上报，这里设置为0
                        h.setIsreportwhite(0);
                        //h.setIsreportmanager(0);
                        h.setUpdate_time(new Date());
                        heartbeatsService.insertHeartbeats(h);
                    }

                    JSONArray jos = new JSONArray();
                    //重新获取心跳数据，判断当前device的上报情况
                    Heartbeats hbss = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                    int isreported = hbss.getIsreportwhite();
                    //其实isreported标志已经没用了，走到这里isreported总为0,但是为了后期需要，暂时保留
                    //现在的机制是：平台下发白名单后还是在command表中将其置为1，表示是新下发的指令，然后找到
                    //heartbeats中将对应device中的faceid（全集），重新上报即可，换句话说就是只要平台操作了
                    //下发白名单就上报，不管是否是新增的faceid
                    if(!flag){
                        if(isreported == 0){
                            //将数据放入JSON中，待取走
                            Iterator<Long> it = updatedFaceid_set.iterator();
                            while (it.hasNext()) {
                                long faceid = it.next();
                                JSONObject jo = new JSONObject();
                                jo.put("face_id", faceid);
                                jos.add(jo);
                            }
                            result.put("white_list", jos);
                            //取走之后将对应heartbeats中的isreport置为1，表示已取走，下次不需要再上报
                            hbss.setIsreportwhite(1);
                            heartbeatsService.updateHeartbeatsByDeviceid(hbss);
                        }
                    }
                    result.put("result", 0);
                }
                //平台下发管理员列表,机制同白名单
                if(managerList.size() != 0){
                    //最新下发管理员的faceid集合
                    Set<Long> newFaceid_set = new TreeSet<Long>();
                    //最新删除的
                    Set<Long> delnewFaceid_set = new TreeSet<Long>();
                    //需要更新的管理员faceid集合
                    Set<Long> updatedFaceid_set = new TreeSet<Long>();
                    for(int i = 0; i < managerList.size(); i++){
                        Command cmd = managerList.get(i);
                        long cmdType = cmd.getCommandtype();
                        long faceid = cmd.getFace_id();
                        //6-下发管理员  7-删除管理员
                        if(cmdType == 6){
                            newFaceid_set.add(faceid);
                        } else if (cmdType == 7){
                            delnewFaceid_set.add(faceid);
                        }
                        //设置为0下次不再筛选
                        cmd.setReport_manager(0);
                        nbCommandService.updateReportByDeviceid(cmd);
                    }
                    //加入到Heatbeats中，以让终端获取
                    Heartbeats heartbeats = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                    if(heartbeats != null){
                        String wl = heartbeats.getManangerfacelist();
                        if(wl != null && !wl.equals("")){
                            String[] originalArr = wl.split(",");
                            for(String arr : originalArr){
                                updatedFaceid_set.add(Long.valueOf(arr));
                            }
                        }
                        //加入新的管理员faceid,删除已删除的
                        updatedFaceid_set.addAll(newFaceid_set);
                        updatedFaceid_set.removeAll(delnewFaceid_set);
                        StringBuilder updatedManagerList = new StringBuilder("");
                        Iterator<Long> its = updatedFaceid_set.iterator();
                        while(its.hasNext()){
                            long faceid = its.next();
                            if(updatedManagerList.toString().equals("")){
                                updatedManagerList.append(faceid);
                            } else{
                                updatedManagerList.append(",").append(faceid);
                            }
                        }
                        heartbeats.setManangerfacelist(updatedManagerList.toString());
                        //平台下发的指令需要置为0，表示还未上报
                        heartbeats.setIsreportmanager(0);
                        heartbeats.setUpdate_time(new Date());
                        heartbeatsService.updateHeartbeatsByDeviceid(heartbeats);
                    } else {
                        StringBuilder updatedManagerList = new StringBuilder("");
                        updatedFaceid_set.addAll(newFaceid_set);
                        Iterator<Long> its = newFaceid_set.iterator();
                        while(its.hasNext()){
                            long faceid = its.next();
                            if(updatedManagerList.toString().equals("")){
                                updatedManagerList.append(faceid);
                            } else{
                                updatedManagerList.append(",").append(faceid);
                            }
                        }
                        Heartbeats h = new Heartbeats();
                        h.setDevice_id(deviceid);
                        h.setManangerfacelist(updatedManagerList.toString());
                        //1-已上报 0-未上报，这里设置为0
                        //h.setIsreportwhite(0);
                        h.setIsreportmanager(0);
                        h.setUpdate_time(new Date());
                        heartbeatsService.insertHeartbeats(h);
                    }

                    JSONArray jos = new JSONArray();
                    //将数据放入JSON中，待取走
                    Iterator<Long> it = updatedFaceid_set.iterator();
                    while (it.hasNext()) {
                        long faceid = it.next();
                        JSONObject jo = new JSONObject();
                        jo.put("face_id", faceid);
                        jos.add(jo);
                    }
                    result.put("manager_list", jos);
                    //取走之后将对应heartbeats中的isreport置为1，表示已取走，下次不需要再上报
                    //重新获取心跳数据，判断当前device的上报情况
                    Heartbeats hbss = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                    hbss.setIsreportmanager(1);
                    heartbeatsService.updateHeartbeatsByDeviceid(hbss);
                    result.put("result", 0);
                }
                //上报平台删除的白名单人员
                if(delWhiteList.size() != 0){
                    //最新删除白名单faceid集合
                    Set<Long> delFaceid_set = new TreeSet<Long>();
                    //需要更新的白名单faceid集合
                    Set<Long> updatedFaceid_set = new TreeSet<Long>();
                    //结果是否需要上报标识
                    Boolean flag = false;
                    for(int i = 0; i < delWhiteList.size();i++){
                        Command cmd = delWhiteList.get(i);
                        String cmdStatus = cmd.getCommandstate();
                        long cmdTime = cmd.getCommandtime().getTime();
                        long faceid = cmd.getFace_id();
                        if(cmdStatus.equals("SUCCESSFUL")){
                            delFaceid_set.add(faceid);
                            cmd.setReport_white(0);
                            nbCommandService.updateReportByDeviceid(cmd);
                        }else if(cmdStatus.equals("TIMEOUT") || cmdStatus.equals("FAILED")){
                            flag = true;
                            //如果失败或者超时，那下次也不再筛选，将command中的report_white置为0,并且不加到心跳返回JSON
                            cmd.setReport_white(0);
                            nbCommandService.updateReportByDeviceid(cmd);
                        }else if(cmdStatus.equals("DELIVERED") || cmdStatus.equals("SENT")){
                            flag = true;
                            //如果指令长时间还是这两种状态，那么也不必总是上报，防止终端不停报授权已更新
                            //DELIVERED和SENT维持15s则下次不再筛选
                            if(System.currentTimeMillis() - cmdTime >= mRecvTimeout){
                                flag = false;
                                cmd.setReport_white(0);
                                nbCommandService.updateReportByDeviceid(cmd);
                            }
                        }
                    }
                    //加入到Heatbeats中，以让终端获取
                    Heartbeats heartbeats = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                    if(heartbeats != null){
                        String wl = heartbeats.getWhitefacelist();
                        if(wl != null && !wl.equals("")){
                            String[] originalArr = wl.split(",");
                            for(String arr : originalArr){
                                updatedFaceid_set.add(Long.valueOf(arr));
                            }
                        }
                        updatedFaceid_set.removeAll(delFaceid_set);
                        StringBuilder updatedWhiteList = new StringBuilder("");
                        Iterator<Long> its = updatedFaceid_set.iterator();
                        while(its.hasNext()){
                            long faceid = its.next();
                            if(updatedWhiteList.toString().equals("")){
                                updatedWhiteList.append(faceid);
                            } else{
                                updatedWhiteList.append(",").append(faceid);
                            }
                        }
                        heartbeats.setWhitefacelist(updatedWhiteList.toString());
                        //平台下发的指令需要置为0，表示还未上报
                        heartbeats.setIsreportwhite(0);
                        heartbeats.setUpdate_time(new Date());
                        heartbeatsService.updateHeartbeatsByDeviceid(heartbeats);
                    } else {
                        //如果查不到记录的话，也没有白名单可以删除，直接置空
                        updatedFaceid_set.clear();
                        Heartbeats h = new Heartbeats();
                        h.setDevice_id(deviceid);
                        h.setManangerfacelist("");
                        //1-已上报 0-未上报，这里设置为0
                        //h.setIsreportwhite(0);
                        h.setIsreportwhite(0);
                        h.setUpdate_time(new Date());
                        heartbeatsService.insertHeartbeats(h);
                    }
                    JSONArray jos = new JSONArray();
                    //将数据放入JSON中，待取走
                    if(!flag){
                        Iterator<Long> it = updatedFaceid_set.iterator();
                        while (it.hasNext()) {
                            long faceid = it.next();
                            JSONObject jo = new JSONObject();
                            jo.put("face_id", faceid);
                            jos.add(jo);
                        }
                    }
                    result.put("white_list", jos);
                    //取走之后将对应heartbeats中的isreport置为1，表示已取走，下次不需要再上报
                    //重新获取心跳数据，判断当前device的上报情况
                    Heartbeats hbss = heartbeatsService.selectHeartbeatsByDeviceid(deviceid);
                    hbss.setIsreportwhite(1);
                    heartbeatsService.updateHeartbeatsByDeviceid(hbss);
                    result.put("result", 0);
                }
            }
        } 
        catch(Exception e)
        {
            result.put("result", 1);
        	e.printStackTrace();
        } 
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        logger.info(result.toString());
        return result;
     }
}

