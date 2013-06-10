package models.article;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import utils.StringUtils;

/**
 * 文章管理
 * @author zhangpeng
 *
 */
@Entity
@Table(name="article")
public class Article extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 文章ID
	 */
	@Id
	public long id;
	
	/**
	 * 文章Code
	 */
	@Column
	public String article_code;
	
	/**
	 * 文章标题
	 */
	@Column
	public String article_title;
	
	/**
	 * 文章内容
	 */
	@Column
	public String article_content;
	
	/**
	 * 文章作者
	 */
	@Column
	public String article_author;
	
	/**
	 * 文章添加时间
	 */
	@Column
	public String article_date;
	
	/**
	 * 文章主题
	 */
	@Column
	public String article_subject;
	
	/**
	 * 文章分类
	 * default:默认分类
	 */
	@Column
	public String article_categroy_code;
	
	/**
	 * 审核状态
	 * 默认:不通过
	 */
	@Column
	public boolean article_auditstatus=false;
	
	public static Model.Finder<Long, Article> find = new Model.Finder<Long, Article>(Long.class, Article.class);
	
	/**
	 * 创建文章
	 * @param article
	 */
	public static void createArticle(Article article){
		article.article_date = StringUtils.getStanderDate();
		article.article_code = StringUtils.getMengCode();
		if(article.article_categroy_code == null || article.article_categroy_code.equals("")){
			article.article_categroy_code = "default";
		}
		article.save();
	}
	
	/**
	 * 修改文章
	 * @param article
	 */
	public static void modifyArticle(Article article){
		/**
		 * 如果数据库中不存在,创建新文章
		 * 修改的文章,均需重新审核
		 */
		if(article.id <= 0){
			createArticle(article);
		}else{
			article.article_date = StringUtils.getStanderDate();
			article.article_auditstatus = false;
			article.update();
		}
	}
	
	/**
	 * 删除文章-ByCode
	 * @param article_code
	 */
	public static void destroyArticle(String article_code){
		Ebean.delete(find.where().eq("article_code", article_code).findList());
	}
	
	/**
	 * 删除文章(批量)
	 * @param article_codes
	 */
	public static void destoryArticle(List<String> article_codes){
		for(String article_code : article_codes){
			destroyArticle(article_code);
		}
	}
	
	
	/**
	 * 发布文章(批量)
	 * @param article_codes
	 */
	public static void publishArticle(List<String> article_codes){
		for(String article_code : article_codes){
			Article article = getArticleByCode(article_code);
			article.article_auditstatus = true;
			article.update();
		}
	}
	
	/**
	 * 查询文章内容-ByCode
	 * @param article_code
	 * @return
	 */
	public static Article getArticleByCode(String article_code){
		return find.where().eq("article_code", article_code).findUnique();
	}
	
	/**
	 * 根据文章分类获取文章列表-ByCategoryCode
	 * @param category_code
	 * @return
	 */
	public static List<Article> getArticlesByCategoryCode(String category_code){
		return find.where().eq("article_category_code", category_code).findList();
	}
	
	/**
	 * 根据分类获取文章分页列表-ByCategoryCode and Page
	 * @param category_code
	 * @param page
	 * @param size
	 * @return
	 */
	public static List<Article> getArticlePageByCategoryCode(String category_code, int page, int size){
		return find.where().eq("article_category_code", category_code).orderBy().desc("article_date").findPagingList(size).getPage(page).getList();
	}
	
	public static List<Article> getArticleTop(String category_code, int top){
		return find.where().eq("article_category_code", category_code).orderBy().desc("article_date").findPagingList(top).getPage(0).getList();
	}
	
}
