import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Category
import com.example.kenyang.databinding.ItemRoundCategoryBinding
import com.example.kenyang.ui.viewmodel.MenuItem

class ItemMenuRecommendationAdapter(private val menuItems: List<MenuItem>) : RecyclerView.Adapter<ItemMenuRecommendationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu_recommendation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.tvMenu.text = menuItem.menuName
        holder.tvStock.text = menuItem.stock
        holder.tvRestaurant.text = menuItem.restaurantName
        holder.tvRating.text = menuItem.rating
        holder.tvDistance.text = menuItem.distance
        holder.tvPrice.text = menuItem.price
        // Optionally set image resource here if needed
        // holder.ivRecommendationImage.setImageResource(menuItem.imageResId)
    }

    override fun getItemCount() = menuItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRecommendationImage: ImageView = itemView.findViewById(R.id.iv_recommendation_image)
        val ivStar: ImageView = itemView.findViewById(R.id.iv_star)
        val tvMenu: TextView = itemView.findViewById(R.id.tv_menu)
        val tvStock: TextView = itemView.findViewById(R.id.tv_stock)
        val tvRestaurant: TextView = itemView.findViewById(R.id.tv_restaurant)
        val tvRating: TextView = itemView.findViewById(R.id.tv_rating)
        val tvDistance: TextView = itemView.findViewById(R.id.tv_distance)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
    }
}
