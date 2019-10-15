drop PROCEDURE if EXISTS BOKE_DELETE_ARTICLE;
CREATE PROCEDURE BOKE_DELETE_ARTICLE(IN p_articleId INTEGER)
BEGIN
	/**
		删除文章及相关
	**/
	delete from boke_article where id = p_articleId;
	delete from boke_article_comment where articleId= p_articleId;
	delete from boke_article_praise where articleId= p_articleId;
	delete from boke_article_save where articleId= p_articleId;
	delete from boke_article_type where articleId= p_articleId;
END;