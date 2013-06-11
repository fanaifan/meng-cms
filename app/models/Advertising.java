package models;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import utils.StringUtils;

/**
 * 广告管理
 * @author zhangpeng
 *
 */
public class Advertising extends Model{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public long id;
	
	/**
	 * 广告Code
	 */
	public String ad_code;
	
	/**
	 * 广告位置
	 */
	public String ad_position;
	
	/**
	 * 广告脚本
	 */
	public String ad_script;
	
	/**
	 * 广告链接URL
	 */
	public String ad_url;
	
	/**
	 * 广告图片
	 */
	public String ad_image;
	
	/**
	 * 广告文字
	 */
	public String ad_text;
	
	/**
	 * 广告类型
	 * IMAGE:图片广告
	 * TEXT:文字广告
	 * IMAGE_TEXT:图文广告
	 * OTHER:广告商广告
	 */
	public String ad_type;
	
	/**
	 * 广告上架
	 */
	public boolean online=true;
	
	/**
	 * 广告到期日期
	 */
	public String endate;
	
	public static Model.Finder<Long, Advertising> find = new Model.Finder<Long, Advertising>(Long.class, Advertising.class);
	
	/**
	 * 创建广告
	 * @param ad
	 */
	public static void create_AD(Advertising ad){
		ad.ad_code = StringUtils.getMengCode();
		ad.save();
	}
	
	/**
	 * 修改广告
	 * @param ad
	 */
	public static void modify_AD(Advertising ad){
		ad.update();
	}
	
	/**
	 * 删除广告
	 * @param ac_code
	 */
	public static void destroy_AD(String ad_code){
		Ebean.delete(find.where().eq("ad_code", ad_code).findList());
	}
	
	/**
	 * 根据广告位置获取广告内容
	 * @param ad_position
	 * @return
	 */
	public static Advertising get_AD(String ad_position){
		return find.where().eq("ad_position", ad_position).findUnique();
	}
	
	/**
	 * 广告上架
	 * @param ad_code
	 */
	public static void online_AD(String ad_code){
		Advertising ad = find.where().eq("ad_code", ad_code).findUnique();
		ad.online = true;
		ad.update();
	}
	
	/**
	 * 广告下架
	 * @param ad_code
	 */
	public static void offline_AD(String ad_code){
		Advertising ad = find.where().eq("ad_code", ad_code).findUnique();
		ad.online = false;
		ad.update();
	}

}
