package com.example.lapokedex

import android.net.Uri
import android.util.Log
import java.io.Console
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils {

    companion object {
        val POKEMON_API_BASEURL = "https://pokeapi.co/api/v2/"
        val POKEMON_BASE_PATH = "pokemon"

        fun buildGeneralPokemonUrl(offset:Int = 0, limit:Int = 0) : URL {
            val builtUri = Uri.parse(POKEMON_API_BASEURL)
                .buildUpon()
                .appendPath(POKEMON_BASE_PATH)
                .appendQueryParameter("offset", offset.toString())
                .appendQueryParameter("limit", limit.toString())
                .build()

            Log.d("URLGENERAL", builtUri.toString())

            return try {
                URL(builtUri.toString())
            }catch (e : MalformedURLException){
                URL("")
            }
        }

        fun buildSinglePokemonUrl(pokemonName: String) : URL {
            val builtUri = Uri.parse(POKEMON_API_BASEURL)
                .buildUpon()
                .appendPath(POKEMON_BASE_PATH)
                .appendPath(pokemonName)
                .build()

            Log.d("URLSINGLE", builtUri.toString())

            return try {
                URL(builtUri.toString())
            }catch (e : MalformedURLException){
                URL("")
            }
        }



        @Throws(IOException::class)
        fun getResponseFromHttpUrl(url: URL):String{
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                val `in` = urlConnection.inputStream

                val scanner = Scanner(`in`)
                scanner.useDelimiter("\\A")

                val hasInput = scanner.hasNext()
                return if(hasInput){
                    scanner.next()
                }else{
                    ""
                }
            }finally {
                urlConnection.disconnect()
            }
        }
    }



}