package org.iplass.tutorial.product;

import org.iplass.gem.command.Constants;
import org.iplass.mtp.ManagerLocator;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.CommandConfig;
import org.iplass.mtp.command.annotation.action.ActionMapping;
import org.iplass.mtp.command.annotation.action.Result;
import org.iplass.mtp.command.annotation.action.Result.Type;
import org.iplass.mtp.entity.Entity;
import org.iplass.mtp.entity.EntityManager;
import org.iplass.mtp.entity.SearchResult;
import org.iplass.mtp.entity.query.Query;

@ActionMapping(name="tutorial/product/java/inputProduct",
    displayName="商品一括登録入力画面(java)",
    privileged=true,
    result=@Result(type=Type.JSP,
        value="/jsp/tutorial/product/bulkInsert.jsp",
        templateName="tutorial/product/java/bulkInsert"),
    command=@CommandConfig(commandClass=InputProduct.class)
)
@CommandClass(name="tutorial/product/java/inputProduct", displayName="商品一括登録画面表示コマンド(java)")
public class InputProduct implements Command {

  @Override
  public String execute(RequestContext request) {
    EntityManager em = ManagerLocator.manager(EntityManager.class);
    // カテゴリ情報の取得
    SearchResult<Entity> categories = em.searchEntity(new Query()
        .select("oid", "name")
        .from("tutorial.product.ProductCategory"));
    request.setAttribute("categories", categories);
    return Constants.CMD_EXEC_SUCCESS;
  }
}