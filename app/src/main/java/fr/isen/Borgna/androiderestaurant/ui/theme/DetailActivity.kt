package fr.isen.Borgna.androiderestaurant

import com.google.gson.annotations.SerializedName

data class DetailActivity(
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()
)

data class Ingredients(
    @SerializedName("id") var id: String? = null,
    @SerializedName("id_shop") var idshop: String? = null,
    @SerializedName("name_fr") var namefr: String? = null,
    @SerializedName("name_en") var nameen: String? = null,
    @SerializedName("create_date") var createdate: String? = null,
    @SerializedName("update_date") var updatedate: String? = null,
    @SerializedName("id_pizza") var idpizza: String? = null
)

data class Objet(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name_fr") var namefr: String? = null,
    @SerializedName("name_en") var nameen: String? = null,
    @SerializedName("id_category") var idcategory: String? = null,
    @SerializedName("categ_name_fr") var categnamefr: String? = null,
    @SerializedName("categ_name_en") var categnameen: String? = null,
    @SerializedName("images") var images: ArrayList<String> = arrayListOf(),
    @SerializedName("ingredients") var ingredients: ArrayList<Ingredients> = arrayListOf(),
    @SerializedName("prices") var prices: ArrayList<Prix> = arrayListOf()
)

data class Data(
    @SerializedName("name_fr") var namefr: String? = null,
    @SerializedName("name_en") var nameen: String? = null,
    @SerializedName("items") var items: ArrayList<Objet> = arrayListOf()
)

data class Prix(
    @SerializedName("id") var id: String? = null,
    @SerializedName("id_pizza") var idpizza: String? = null,
    @SerializedName("id_size") var idsize: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("create_date") var createdate: String? = null,
    @SerializedName("update_date") var updatedate: String? = null,
    @SerializedName("size") var size: String? = null
)