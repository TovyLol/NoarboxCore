package projects.tovy.github.ServerUsage.Shops;
import org.bukkit.entity.Player;

public class GetShops {
    private ShopFunctions func;
    public GetShops(ShopFunctions func) {
        this.func = func;
    }

    //proof of concept
    public void getShulkerShop(Player p) {
        func.funcShulkerShop(p);

    }

}
