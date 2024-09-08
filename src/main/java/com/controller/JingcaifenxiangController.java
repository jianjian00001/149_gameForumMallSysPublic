package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.JingcaifenxiangEntity;
import com.entity.view.JingcaifenxiangView;

import com.service.JingcaifenxiangService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.CommonUtil;


/**
 * 精彩分享
 * 后端接口
 * @author 
 * @email 
 * @date 2020-11-13 11:47:06
 */
@RestController
@RequestMapping("/jingcaifenxiang")
public class JingcaifenxiangController {
    @Autowired
    private JingcaifenxiangService jingcaifenxiangService;
    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,JingcaifenxiangEntity jingcaifenxiang, HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			jingcaifenxiang.setZhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<JingcaifenxiangEntity> ew = new EntityWrapper<JingcaifenxiangEntity>();
		PageUtils page = jingcaifenxiangService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jingcaifenxiang), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,JingcaifenxiangEntity jingcaifenxiang, HttpServletRequest request){
        EntityWrapper<JingcaifenxiangEntity> ew = new EntityWrapper<JingcaifenxiangEntity>();
		PageUtils page = jingcaifenxiangService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, jingcaifenxiang), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( JingcaifenxiangEntity jingcaifenxiang){
       	EntityWrapper<JingcaifenxiangEntity> ew = new EntityWrapper<JingcaifenxiangEntity>();
      	ew.allEq(MPUtil.allEQMapPre( jingcaifenxiang, "jingcaifenxiang")); 
        return R.ok().put("data", jingcaifenxiangService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(JingcaifenxiangEntity jingcaifenxiang){
        EntityWrapper< JingcaifenxiangEntity> ew = new EntityWrapper< JingcaifenxiangEntity>();
 		ew.allEq(MPUtil.allEQMapPre( jingcaifenxiang, "jingcaifenxiang")); 
		JingcaifenxiangView jingcaifenxiangView =  jingcaifenxiangService.selectView(ew);
		return R.ok("查询精彩分享成功").put("data", jingcaifenxiangView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        JingcaifenxiangEntity jingcaifenxiang = jingcaifenxiangService.selectById(id);
        return R.ok().put("data", jingcaifenxiang);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") String id){
        JingcaifenxiangEntity jingcaifenxiang = jingcaifenxiangService.selectById(id);
        return R.ok().put("data", jingcaifenxiang);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R thumbsup(@PathVariable("id") String id,String type){
        JingcaifenxiangEntity jingcaifenxiang = jingcaifenxiangService.selectById(id);
        if(type.equals("1")) {
        	jingcaifenxiang.setThumbsupnum(jingcaifenxiang.getThumbsupnum()+1);
        } else {
        	jingcaifenxiang.setCrazilynum(jingcaifenxiang.getCrazilynum()+1);
        }
        jingcaifenxiangService.updateById(jingcaifenxiang);
        return R.ok();
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody JingcaifenxiangEntity jingcaifenxiang, HttpServletRequest request){
    	jingcaifenxiang.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jingcaifenxiang);

        jingcaifenxiangService.insert(jingcaifenxiang);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody JingcaifenxiangEntity jingcaifenxiang, HttpServletRequest request){
    	jingcaifenxiang.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(jingcaifenxiang);

        jingcaifenxiangService.insert(jingcaifenxiang);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody JingcaifenxiangEntity jingcaifenxiang, HttpServletRequest request){
        //ValidatorUtils.validateEntity(jingcaifenxiang);
        jingcaifenxiangService.updateById(jingcaifenxiang);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        jingcaifenxiangService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<JingcaifenxiangEntity> wrapper = new EntityWrapper<JingcaifenxiangEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			wrapper.eq("zhanghao", (String)request.getSession().getAttribute("username"));
		}

		int count = jingcaifenxiangService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	


}
