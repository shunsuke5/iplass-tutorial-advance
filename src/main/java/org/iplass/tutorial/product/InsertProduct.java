package org.iplass.tutorial.product;

import org.iplass.mtp.ManagerLocator;
import org.iplass.mtp.command.Command;
import org.iplass.mtp.command.RequestContext;
import org.iplass.mtp.command.annotation.CommandClass;
import org.iplass.mtp.command.annotation.action.ActionMapping;
import org.iplass.mtp.command.annotation.action.Result;
import org.iplass.mtp.command.annotation.action.Result.Type;
import org.iplass.mtp.command.annotation.webapi.RestJson; 
import org.iplass.mtp.command.annotation.webapi.WebApi; 
import org.iplass.mtp.entity.Entity;
import org.iplass.mtp.entity.EntityManager;
import org.iplass.mtp.entity.GenericEntity;
import org.iplass.mtp.util.StringUtil;
import org.iplass.mtp.web.template.TemplateUtil;
import org.iplass.mtp.webapi.definition.RequestType; 
import org.iplass.gem.command.Constants;

@ActionMapping(name="tutorial/product/java/insertProduct",
    displayName="商品一括登録処理(java)",
    privileged=true,
    result=@Result(type=Type.REDIRECT, value="dispInput"))
@WebApi(name = "tutorial/product/java/insertProduct",
displayName = "商品一括登録処理(java)",
privilaged = true,
accepts = RequestType.REST_JSON,
restJson = @RestJson(parameterName = "param"))
@CommandClass(name="tutorial/product/java/InsertProduct", displayName="商品一括登録コマンド(java)")
public class InsertProduct implements Command {

    @Override
    public String execute(RequestContext request) {
        EntityManager em = ManagerLocator.manager(EntityManager.class);
        String[] productNames = request.getParams("productName");
        String[] productCategories = request.getParams("productCategory");
        String[] productPrices = request.getParams("productPrice");
        for (int i = 0; i < productNames.length; i++) {
            if (StringUtil.isNotEmpty(productNames[i])) {
                Entity entity = new GenericEntity();
                entity.setDefinitionName("tutorial.product.Product");
                entity.setName(productNames[i]);
                Entity refEntity = new GenericEntity();
                refEntity.setValue("oid", productCategories[i]);
                entity.setValue("productCategory", refEntity);
                entity.setValue("price", productPrices[i]);

                em.insert(entity);
            }
        }
        request.setAttribute("dispInput", TemplateUtil.getTenantContextPath() + "/tutorial/product/java/inputProduct");
        return Constants.CMD_EXEC_SUCCESS;
    }
}
