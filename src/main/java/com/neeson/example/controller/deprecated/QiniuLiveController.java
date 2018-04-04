package com.neeson.example.controller.deprecated;

import com.neeson.example.dto.QiniuDTO;
import com.neeson.example.properties.QiniuLiveProperties;
import com.neeson.example.util.CodeUtil;
import com.neeson.example.util.exception.MyException;
import com.neeson.example.util.response.ResponseResult;
import com.neeson.example.util.response.RestResultGenerator;
import com.qiniu.pili.Client;
import com.qiniu.pili.Hub;
import com.qiniu.pili.PiliException;
import com.qiniu.pili.Stream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author neeson
 */
@Api("七牛相关api")
@RestController
@RequestMapping("qiniu")
@Slf4j
public class QiniuLiveController {

   @Autowired
   private QiniuLiveProperties qiniuProperties;

    @Autowired
    private CodeUtil codeUtil;

    @GetMapping("/create")
    public ResponseResult create() {
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        String streamKey = codeUtil.createUUID(16);
        //String streamKey = "test02";
        Map urls = new HashMap(5);
        try {
            Stream stream = hub.create(streamKey);
            stream.save(0, 0);
            String rtmpPublishURL = cli.RTMPPublishURL(qiniuProperties.getRtmpPublishDomain() , qiniuProperties.getHubName(), streamKey,3600);
            String rtmpPlayURL = cli.RTMPPlayURL(qiniuProperties.getRtmpPlayDomain() , qiniuProperties.getHubName(), streamKey);
            //String hslPlayURL = cli.HLSPlayURL(hslPlayDomain,hubName,streamKey);
            //String hdlPlayURL = cli.HDLPlayURL(hdlPlayDomain,hubName,streamKey);
            String snapShotURL = cli.SnapshotPlayURL(qiniuProperties.getSnapShotDomain(), qiniuProperties.getHubName(), streamKey);
            urls.put("rtmpPublishURL", rtmpPublishURL);
            urls.put("rtmpPlayURL", rtmpPlayURL);
            //urls.put("hslPlayURL",hslPlayURL);
            //urls.put("hdlPlayURL",hdlPlayURL);
            urls.put("snapShotURL", snapShotURL);
        } catch (PiliException e) {
            throw new MyException("获取流信息失败");
        }
        return RestResultGenerator.genResult(urls, "获取流成功");
    }

    @ApiOperation(value = "获取所有直播流")
    @GetMapping("/streams")
    public ResponseResult listLiveStream() {
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        List<String> streams;
        try {
            Hub.ListRet list = hub.list("", 0, "");
            String[] keys = list.keys;
            streams = new ArrayList<>(keys.length);
            for (String key : keys) {
                streams.add(cli.RTMPPlayURL(qiniuProperties.getRtmpPlayDomain() ,qiniuProperties.getHubName(), key));
            }
        } catch (PiliException e) {
            throw new MyException("获取所有流失败");
        }
        return RestResultGenerator.genResult(streams, "");
    }

    @ApiOperation("获取正在直播的流")
    @GetMapping("/stream/living")
    public ResponseResult listLivingStream() {
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        List<String> streams;
        try {
            Hub.ListRet listRet = hub.listLive("", 0, "");
            streams = new ArrayList<>(listRet.keys.length);
            for (String key : listRet.keys) {
                streams.add(cli.RTMPPlayURL(qiniuProperties.getRtmpPlayDomain() ,qiniuProperties.getHubName() , key));
            }
        } catch (PiliException e) {
            throw new MyException("获取直播流失败");
        }
        return RestResultGenerator.genResult(streams, "获取正在直播流成功");
    }

    @ApiOperation("禁用流")
    @ApiImplicitParam(name = "streamKey", value = "流key", required = true, paramType = "path", dataType = "String")
    @GetMapping("/stream/disable/{streamKey}")
    public ResponseResult disableStream(@PathVariable String streamKey) {
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        try {
            Stream stream = hub.get(streamKey);
            stream.disable();
        } catch (PiliException e) {
            throw new MyException("禁播失败");
        }
        return RestResultGenerator.genResult("禁播成功");
    }


    @ApiOperation("开启流")
    @ApiImplicitParam(name = "streamKey", value = "流key", required = true, paramType = "path", dataType = "String")
    @GetMapping("/stream/enable/{streamKey}")
    public ResponseResult enableStream(@PathVariable String streamKey) {
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        try {
            Stream stream = hub.get(streamKey);
            stream.enable();
        } catch (PiliException e) {
            throw new MyException("开启流失败");
        }
        return RestResultGenerator.genResult("开启流成功");
    }

    @ApiOperation("获取直播状态")
    @ApiImplicitParam(name = "streamKey", value = "流key", required = true, paramType = "path", dataType = "String")
    @GetMapping("/stream/status/{streamKey}")
    public ResponseResult getLiveStatus(@PathVariable String streamKey) {
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        Stream.LiveStatus liveStatus;
        try {
            Stream stream = hub.get(streamKey);
            liveStatus = stream.liveStatus();
        } catch (PiliException e) {
            throw new MyException("查询流状态失败");
        }
        return RestResultGenerator.genResult(liveStatus, "查询流状态成功");
    }


    @ApiOperation("获取推流历史")
    @ApiImplicitParam(name = "streamKey", value = "流key", required = true, paramType = "path", dataType = "String")
    @GetMapping("/stream/record/{streamKey}")
    public ResponseResult getStreamRecord(@PathVariable String streamKey){
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        Stream.Record[] records;
        try {
            Stream stream = hub.get(streamKey);
            records = stream.historyRecord(0, 0);
        } catch (PiliException e) {
            throw new MyException("获取推流历史失败");
        }
        return RestResultGenerator.genResult(records,"获取推流历史成功");
    }

    @ApiOperation("保存直播记录")
    @ApiImplicitParam(name = "qiniuDTO", value = "时间段流", required = true, dataType = "QiniuDTO")
    @PostMapping("/stream/save")
    public ResponseResult saveStream(@RequestBody QiniuDTO qiniuDTO){
        log.debug("==========>"+qiniuDTO.getStreamKey());
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        String save;
        try {
            Stream stream = hub.get(qiniuDTO.getStreamKey());
             save = stream.save(qiniuDTO.getStart(), qiniuDTO.getEnd());
        } catch (PiliException e) {
            throw new MyException("保存记录失败");
        }
        return RestResultGenerator.genResult(save,"设置保存成功");
    }

    @ApiOperation("保存直播记录2")
    @ApiImplicitParam(name = "qiniuDTO", value = "时间段流", required = true, dataType = "QiniuDTO")
    @PostMapping("/stream/save2")
    public ResponseResult saveStream2(@RequestBody QiniuDTO qiniuDTO){
        log.debug("==========>"+qiniuDTO.getStreamKey());
        Client cli = new Client(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
        Hub hub = cli.newHub(qiniuProperties.getHubName());
        Map<String, String> ret;
        try {
            Stream stream = hub.get(qiniuDTO.getStreamKey());
            Stream.SaveOptions options = new Stream.SaveOptions();
            options.start = qiniuDTO.getStart();
            options.end = qiniuDTO.getEnd();
            options.format = "mp4";

            ret = stream.saveReturn(options);

        } catch (PiliException e) {
            throw new MyException("保存记录失败");
        }
        return RestResultGenerator.genResult(ret,"设置保存成功");
    }

}
