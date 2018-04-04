package com.neeson.example.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.live.model.v20161101.*;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.neeson.example.dto.AliLiveDTO;
import com.neeson.example.properties.AliLiveProperties;
import com.neeson.example.util.CodeUtil;
import com.neeson.example.util.TimeUtils;
import com.neeson.example.util.exception.MyException;
import com.neeson.example.util.response.ResponseResult;
import com.neeson.example.util.response.RestResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/28
 * Time: 11:31
 * Description:
 */
@Api("阿里直播")
@RestController
@RequestMapping("/ali")
@Slf4j
public class AliController {

    @Autowired
    private AliLiveProperties aliLiveProperties;

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private DefaultAcsClient client;

    @ApiOperation("获取推流")
    @GetMapping("/live/create")
    public ResponseResult createLive() {
        String streamName = "xuexitest";
        Map<String,String> vo = codeUtil.getPushStream(streamName);
        return RestResultGenerator.genResult(vo, "获取推流");

    }


    @ApiOperation("查询直播流历史在线人数")
    @ApiImplicitParam(name = "aliLiveDTO", value = "阿里直播数据", required = true, dataType = "AliLiveDTO")
    @PostMapping("/live/history/user/num")
    public ResponseResult getLiveHistoryUserNum(@RequestBody AliLiveDTO aliLiveDTO) {
        //DefaultAcsClient client = initClient();
        DescribeLiveStreamHistoryUserNumRequest request = new DescribeLiveStreamHistoryUserNumRequest();
        aliLiveDTO.setDomainName(aliLiveProperties.getDomainName());
        BeanUtils.copyProperties(aliLiveDTO, request);
        try {
            DescribeLiveStreamHistoryUserNumResponse acsResponse = client.getAcsResponse(request);
            return RestResultGenerator.genResult(acsResponse, "查询直播流历史在线人数成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("查询直播流历史在线人数失败");
        }
    }


    @ApiOperation("查询在线人数")
    @GetMapping("/live/user/num")
    public ResponseResult listUserNum() {
        //DefaultAcsClient client = initClient();
        DescribeLiveStreamOnlineUserNumRequest request = new DescribeLiveStreamOnlineUserNumRequest();
        request.setDomainName(aliLiveProperties.getDomainName());
        try {
            DescribeLiveStreamOnlineUserNumResponse acsResponse = client.getAcsResponse(request);
            return RestResultGenerator.genResult(acsResponse, "查询在线人数成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("查询在线人数失败");
        }
    }

    @ApiOperation("查询直播流历史帧率和码率")
    @GetMapping("/stream/fps/list")
    public ResponseResult listHistoryFPSStream() {
        //DefaultAcsClient client = initClient();
        DescribeLiveStreamsFrameRateAndBitRateDataRequest request = new DescribeLiveStreamsFrameRateAndBitRateDataRequest();
        request.setDomainName(aliLiveProperties.getDomainName());
        request.setStartTime(TimeUtils.getUTCTime(LocalDateTime.now().minusDays(10)));
        request.setEndTime(TimeUtils.getUTCTime(LocalDateTime.now()));
        try {
            DescribeLiveStreamsFrameRateAndBitRateDataResponse acsResponse = client.getAcsResponse(request);
            return RestResultGenerator.genResult(acsResponse, "查询直播流历史帧率和码率成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("查询直播流历史帧率和码率失败");
        }
    }

    @ApiOperation("查询流控历史")
    @GetMapping("/stream/history/list")
    public ResponseResult listHistoryStream() {
        //DefaultAcsClient client = initClient();
        DescribeLiveStreamsControlHistoryRequest request = new DescribeLiveStreamsControlHistoryRequest();
        request.setDomainName(aliLiveProperties.getDomainName());
        request.setStartTime(TimeUtils.getUTCTime(LocalDateTime.now().minusDays(10)));
        request.setEndTime(TimeUtils.getUTCTime(LocalDateTime.now()));
        try {
            DescribeLiveStreamsControlHistoryResponse acsResponse = client.getAcsResponse(request);
            return RestResultGenerator.genResult(acsResponse, "获取某段时间流历史操作记录成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("获取某段时间流历史操作记录失败");
        }
    }


    @ApiOperation("查询推流黑名单列表")
    @GetMapping("/stream/disable/list")
    public ResponseResult listDisableStream() {
        //DefaultAcsClient client = initClient();
        DescribeLiveStreamsBlockListRequest request = new DescribeLiveStreamsBlockListRequest();
        request.setDomainName(aliLiveProperties.getDomainName());
        try {
            DescribeLiveStreamsBlockListResponse acsResponse = client.getAcsResponse(request);
            return RestResultGenerator.genResult(acsResponse, "获取黑名单成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("获取黑名单失败");
        }
    }


    @ApiOperation("查询推流在线列表")
    @GetMapping("/stream/list")
    public ResponseResult listLivingStream() {
        //DefaultAcsClient client = initClient();
        DescribeLiveStreamsOnlineListRequest request = new DescribeLiveStreamsOnlineListRequest();
        request.setDomainName(aliLiveProperties.getDomainName());
        try {
            DescribeLiveStreamsOnlineListResponse acsResponse = client.getAcsResponse(request);
            return RestResultGenerator.genResult(acsResponse, "获取直播流成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("获取流失败");
        }
    }


    @ApiOperation("禁止直播流推送")
    @ApiImplicitParam(name = "aliLiveDTO", value = "阿里直播数据", required = true, dataType = "AliLiveDTO")
    @PostMapping("/stream/disable")
    public ResponseResult disableStream(@RequestBody AliLiveDTO aliLiveDTO) {
        //DefaultAcsClient client = initClient();
        ForbidLiveStreamRequest request = new ForbidLiveStreamRequest();
        request.setDomainName(aliLiveProperties.getDomainName());
        request.setAppName(aliLiveDTO.getAppName());
        request.setStreamName(aliLiveDTO.getStreamName());
        ForbidLiveStreamResponse acsResponse;
        try {
            acsResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("禁播流失败");
        }
        return RestResultGenerator.genResult(acsResponse, "禁播流成功");
    }

    @ApiOperation("恢复直播流推送")
    @ApiImplicitParam(name = "aliLiveDTO", value = "阿里直播数据", required = true, dataType = "AliLiveDTO")
    @PostMapping("/stream/enable")
    public ResponseResult enableStream(@RequestBody AliLiveDTO aliLiveDTO) {
        //DefaultAcsClient client = initClient();
        ResumeLiveStreamRequest request = new ResumeLiveStreamRequest();
        request.setDomainName(aliLiveProperties.getDomainName());
        request.setAppName(aliLiveDTO.getAppName());
        request.setStreamName(aliLiveDTO.getStreamName());
        try {
            ResumeLiveStreamResponse response = client.getAcsResponse(request);
            return RestResultGenerator.genResult(response, "开启流成功");
        } catch (ClientException e) {
            e.printStackTrace();
            throw new MyException("开启流失败");
        }
    }


    @Bean
    public DefaultAcsClient initClient() {
        IClientProfile profile = DefaultProfile.getProfile(aliLiveProperties.getRegionId(), aliLiveProperties.getAccesskey(), aliLiveProperties.getAccessSecret());
        return new DefaultAcsClient(profile);
    }

}
