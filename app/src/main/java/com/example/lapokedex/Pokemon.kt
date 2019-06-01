package com.example.lapokedex

data class Pokemon(
    var id: String = "0",
    var name: String = "N/A",
    var abilities: ArrayList<AbilityDummy> = ArrayList(),
    var sprites: Sprites

) {
    inner class AbilityDummy(
        var is_hidden: String = "false",
        var ability: Ability
    ){
        inner class Ability(var name: String = "N/A")
    }

    inner class Sprites(
        var back_default:String = "N/A",
        var back_female: String = "N/A",
        var back_shiny: String = "N/A",
        var back_shiny_female: String = "N/A",
        var front_default: String = "N/A",
        var front_female: String = "N/A",
        var front_shiny: String = "N/A",
        var front_shiny_female: String = "N/A"
    )
}
