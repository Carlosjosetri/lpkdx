package com.example.lapokedex

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var pokemonList: ArrayList<Pokemon> = ArrayList()
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_action.setOnClickListener {
            pokemonList.forEach {
                Log.d("POKE", it.id)
                Log.d("POKE", it.name)
                Log.d("POKE", it.sprites.front_default)
                Log.d("POKE", it.abilities[0].ability.name)
                Log.d("POKE", "---------------")
            }

            Log.d("SIZE", pokemonList.size.toString())
        }

        btn_get.setOnClickListener {
            i += 20
            FetchPokemons().execute(i, 20)
        }
        FetchPokemons().execute(i, 20)


        var vAdapter = PokemonAdapter(pokemonList) {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
        }

        var manager = LinearLayoutManager(this)

        with(rv_pokemon_list) {
            adapter = vAdapter
            layoutManager = manager
        }
    }

    fun addPokemonToList(pokemon: Pokemon) {
        pokemonList.add(pokemon)
    }

    inner class FetchPokemons : AsyncTask<Int, Void, String>() {
        override fun doInBackground(vararg params: Int?): String {
            /*
            * Pos 0 = offset
            * Pos 1 = limit
            * */

            val offset = params[0]
            val limit = params[1]

            val url = NetworkUtils.buildGeneralPokemonUrl(offset!!, limit!!)

            return try {
                NetworkUtils.getResponseFromHttpUrl(url)
            } catch (e: IOException) {
                ""
            }

        }

        override fun onPostExecute(pokeInfoList: String?) {
            super.onPostExecute(pokeInfoList)
            val pokeList = JSONObject(pokeInfoList).getJSONArray("results")
            for (i in 0 until pokeList.length()) {
                FetchPokemon().execute(JSONObject(pokeList[i].toString()).getString("name"))
            }
        }
    }

    inner class FetchPokemon : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String {

            val pokemonName = params[0]

            val url = NetworkUtils.buildSinglePokemonUrl(pokemonName)

            return try {
                NetworkUtils.getResponseFromHttpUrl(url)
            } catch (e: IOException) {
                ""
            }

        }

        override fun onPostExecute(pokeInfo: String?) {
            super.onPostExecute(pokeInfo)

            if (pokeInfo != null) {
                if (!pokeInfo.isEmpty()) {
                    val pokemon = Gson().fromJson<Pokemon>(pokeInfo, Pokemon::class.java)
                    addPokemonToList(pokemon)
                } else {
                    Toast.makeText(this@MainActivity, "No existe en la base de datos", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
