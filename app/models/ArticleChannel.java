package models;

import java.util.List;

import play.db.ebean.Model;

/**
 * 文章频道管理
 * @author zhangpeng
 *
 */
public class ArticleChannel {
	
	/**
	 * 文章Find
	 */
	public static Model.Finder<Long, Article> a_find = new Model.Finder<Long, Article>(Long.class, Article.class);
	/**
	 * 文章分类Find
	 */
	public static Model.Finder<Long, ArticleCategory> ac_find = new Model.Finder<Long, ArticleCategory>(Long.class, ArticleCategory.class);
	
	
	/**
	 * 获取顶级文章列表(频道)
	 * @return
	 */
	public static List<ArticleCategory> getChannels(){
		return ac_find.where().eq("parent_category_code", "channel").findList();
	}
	
	/**
	 * 获取该频道下的文章
	 * @param category_code
	 * @param page
	 * @param size
	 * @return
	 */
	public static List<Article> getArticleByChannel(String category_code, int page, int size){
		return Article.getArticlePageByCategoryCode(category_code, page, size);
	}
}
