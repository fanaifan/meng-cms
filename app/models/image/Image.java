package models.image;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import utils.StringUtils;

/**
 * 图片管理
 * @author zhangpeng
 *
 */
public class Image extends Model{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long id;
	/**
	 * 图片Code
	 */
	public String img_code;
	/**
	 * 图片URL
	 */
	public String url;
	/**
	 * 图片的字节码
	 * 用于存储上传图片
	 */
	public String byte_code;
	/**
	 * 图片存放的路径
	 * 用于存储上传图片的服务器路径
	 */
	public String image_path;
	
	/**
	 * 图片类型
	 * URL:网络图片
	 * UPLOAD:自行上传图片
	 */
	public String image_type;
	
	/**
	 * 所属频道的Code
	 */
	public String channel_code;
	
	/**
	 * 所属文章分类的Code
	 */
	public String category_code;
	
	/**
	 * 所属文章的Code
	 */
	public String article_code;
	
	public static Model.Finder<Long, Image> find = new Model.Finder<Long, Image>(Long.class, Image.class);
	
	/**
	 * 创建图片
	 * @param img
	 */
	public static void createImage(Image img){
		img.img_code = StringUtils.getMengCode();
		img.save();
	}
	
	/**
	 * 修改图片
	 * @param img
	 */
	public static void modifyImage(Image img){
		//TODO 分析图片的类型,具体分析
		
	}
	
	/**
	 * 删除图片
	 * @param img_code
	 */
	public static void destroyImage(String img_code){
		Ebean.delete(find.where().eq("img_code", img_code).findList());
	}
	
	/**
	 * 获取图片-ByCode
	 * @param img_code
	 * @return
	 */
	public static Image getImage(String img_code){
		return find.where().eq("img_code", img_code).findUnique();
	}
}
