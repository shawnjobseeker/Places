package org.udacityexamples;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import org.udacityexamples.BaseActivityInterface;
import org.udacityexamples.Presenter;
import org.udacityexamples.model.Result;

import java.util.List;
import java.util.Set;

/**
 * Created by Shawn Li on 2/18/2017.
 */

public interface ListFragmentInterface {
    void passResultSet(Set<Result> results, String status);
    View getView();
    Activity getActivity();
    void openPlaceCard(Result result, boolean openedFromMap);
    Result getNextResult();
    Result getPreviousResult();
    void showResultsInMap();
    Bitmap scaleBitmap(int resource, int height, int width);
    void onMapModeChanged(boolean mapMode);
   // void setTag(String tag);
}

/*
Kanto (RB)
Tall grass:
Route 1 - Pidgey, Rattata
Route 22 - NidoranM, NidoranF, Spearow
Route 2 - Pidgey, Caterpie OR Weedle
Viridian Forest - Caterpie, Weedle, Metapod OR Kakuna, Pikachu
Route 3 - Pidgey, Spearow, Jigglypuff
Route 4 - Rattata, Ekans OR Sandshrew
Routes 24 and 25 - Caterpie OR Weedle, Metapod OR Kakuna, Oddish OR Bellsprout, Abra
Routes 5 and 6 - Pidgey, Mankey OR Meowth, Oddish OR Bellsprout
Route 11 - Spearow, Ekans OR Sandshrew, Drowzee
Route 9 - Rattata, Spearow, Ekans OR Sandshrew
Route 10 -  Spearow, Ekans OR Sandshrew, Voltorb
Routes 7 and 8 - Pidgey, Pidgeotto, Growlithe OR Vulpix
Routes 12, 13, 14 and 15 - Pidgey, Pidgeotto, Oddish OR Bellsprout, Gloom OR Weepinbell, Venonat
Routes 16, 17 and 18 - Rattata, Raticate, Spearow, Fearow, Doduo
Safari Zone Area 1 - NidoranM, NidoranF, Exeggcute, Chansey
Safari Zone Area 2 - NidoranM, Nidorino, Rhyhorn, Kangaskhan
Safari Zone Area 3 - NidoranF, Nidorina, Venonat, Venomoth, Scyther OR Pinsir
Safari Zone Area 4 - Paras, Parasect, Doduo, Tauros
Route 21 - Ditto, Tangela
Route 23 - Spearow, Fearow, Arbok OR Sandslash

Cave or unknown building:
Mt. Moon - Zubat, Paras, Geodude, Clefairy
Diglett's Cave - Diglett, Dugtrio
Rock Tunnel - Zubat, Machop, Geodude, Onix
Pokémon Tower - Gastly, Cubone
Seafoam Islands - Zubat, Golbat, Seel, Dewgong, Psyduck OR Slowpoke, Golduck OR Slowbro, Articuno (only one)
Power Plant - Pikachu, Magnemite, Voltorb, Electrode, Electabuzz (Red only), Zapdos (only one)
Pokémon Mansion - Growlithe OR Vulpix, Koffing OR Grimer, Ponyta, Magmar (Blue only)
Victory Road - Zubat, Golbat, Machop, Machoke, Geodude, Graveler, Onix, Moltres (only one)
Cerulean Cave - Golbat, Kadabra, Machoke, Graveler, Onix, Ditto, Mewtwo (only one)

Surfing:
Routes 10, 12, 19, 20 and 21, Vermilion City, Seafoam Islands, Cinnabar Island - Tentacool, Tentacruel
Routes 6, 23, 24 and 25, Viridian City, Cerulean City, Celadon City, Safari Zone - Psyduck OR Slowpoke
Cerulean Cave - Golbat, Tentacruel

Fishing:
Old Rod (all waters) - Magikarp
Good Rod:
Routes 10, 12, 19, 20 and 21, Vermilion City, Seafoam Islands, Cinnabar Island - Magikarp, Tentacool
Routes 6, 23, 24 and 25, Viridian City, Cerulean City, Celadon City - Magikarp, Poliwag
Safari Zone - Magikarp, Goldeen
Super Rod:
Routes 10 and 12 - Gyarados
Routes 19, 20 and 21 - Horsea, Krabby
Vermilion City, Seafoam Islands, Cinnabar Island - Shellder OR Staryu
Routes 6, 23, 24 and 25, Viridian City, Cerulean City, Celadon City - Poliwag, Poliwhirl
Safari Zone - Goldeen, Seaking, Dratini

Non-encountered Pokémon:
Bulbasaur OR Charmander OR Squirtle - Starter
Farfetch'd - Trade Spearow in house in Vermilion City
Mr. Mime - Trade Abra in house in Route 2
Jynx - Trade Poliwhirl in house in Cerulean City
Eevee - Obtain from top of Celadon Condominiums
Porygon - Game Corner Prize (9999 OR 6500 coins)
Hitmonlee OR Hitmonchan - Prize for defeating Black Belt in Saffron
Lickitung - Trade Golduck OR Slowbro in western gate in Fuchsia City
Lapras - Obtain from Silph employee
Omanyte - Trade Helix Fossil in lab in Cinnabar Island
Kabuto - Trade Dome Fossil in lab in Cinnabar Island
Aerodactyl - Trade Old Amber in lab in Cinnabar Island

Johto and Kanto (GS)
Tall grass:
Routes 29 and 1 - Sentret, Pidgey (Morn, Day), Rattata (Night, Morn), Hoothoot (Night)
Route 46 - Spearow, Jigglypuff (Morn, Day), Zubat (Night), Geodude (all times)
Route 30 - Caterpie OR Weedle, Metapod OR Kakuna, Pidgey (Morn, Day), Hoothoot (Night), Spinarak (Night) OR Ledyba (Morn)
Route 31 - Caterpie OR Weedle, Metapod OR Kakuna, Bellsprout (Morn, Day), Zubat (Night), Spinarak (Night) OR Ledyba (Morn)
Route 32 - Bellsprout, Mareep (Morn, Day), Hoppip (Day), Zubat, Wooper (Night), Ekans (Silver only - all times)
Route 33 - Mareep (Morn, Day), Hoppip (Day), Zubat (Night), Ekans (Silver only - all times)
Route 34 - Pidgey (Morn, Day), Hoothoot (Night), Drowzee, Abra, Ditto (all times)
Route 35 - Pidgey (Morn, Day), Hoothoot (Night), Yanma (Day, during swarm only), NidoranM, NidoranF (all times)
National Park - Caterpie OR Weedle, Metapod OR Kakuna, Pidgey (Morn, Day), Sunkern (Day), Hoothoot, Oddish (Night)
Bug-Catching Contest - Caterpie, Metapod, Butterfree, Weedle, Kakuna, Beedrill, Paras, Venonat, Scyther, Pinsir
Routes 36 and 37 - Pidgey, Growlithe OR Vulpix (Morn, Day), Venonat, Hoothoot (Night), Spinarak (Night) OR Ledyba (Morn), Stantler OR Girafarig (all times)
(Also on Route 36, use SquirtBottle on tree to encounter Sudowoodo)
Route 38 - Farfetch'd (Morn, Day), Rattata (Night), Mankey OR Meowth, Miltank (all times), Snubbull (all times, during swarm only)
Route 39 - Farfetch'd (Morn, Day), Rattata (Night), Magnemite, Miltank (all times), Tauros (all times, during swarm only)
Route 42 - Spearow (Morn, Day), Meowth OR Mankey (all times)
Route 43 - Pidgey, Pidgeotto, Mareep, Flaaffy (Morn, Day), Venonat, Hoothoot, Noctowl (Night), Stantler OR Girafarig (all times)
Ruins of Alph - Natu, Smeargle (all times)
Route 44 - Bellsprout, Weepinbell (Morn, Day), Oddish, Gloom (Night), Lickitung, Tangela (all times)
Route 45 - Teddiursa OR Phanpy (Morn, Day), Zubat (Night), Gligar OR Skarmory, Geodude, Graveler (all times)
Routes 26 and 27 - Ponyta, Doduo (Morn, Day), Raticate, Quagsire (Night), Arbok (Silver only - all times)
Routes 5 and 6 - Pidgey, Pidgeotto, Bellsprout, Weepinbell (Morn, Day), Hoothoot, Noctowl, Oddish, Gloom (Night), Meowth OR Mankey (all times)
Route 10 - Voltorb, Electabuzz (all times)
Route 11 - Fearow (Morn, Day), Raticate (Night), Magnemite, Drowzee (all times)
Routes 7 and 8 - Pidgey, Pidgeotto, Growlithe OR Vulpix (Morn, Day), Murkrow, Houndour (Night)
Routes 4 and 9 - Fearow (Morn, Day), Raticate (Night), Arbok (Silver only - all times)
Routes 12, 13, 14 and 15 - Bellsprout, Weepinbell (Morn, Day), Oddish, Gloom (Night), Skiploom, Chansey (Day), Nidorina, Nidorino (all times)
Routes 16, 17 and 18 - Fearow, Doduo, Grimer, Slugma
Route 3 - Pidgey, Jigglypuff (Morn, Day), Zubat (Night)
Route 2 - Caterpie OR Weedle, Metapod OR Kakuna, Pikachu (Morn, Day), Hoothoot (Night), Rattata (Night, Morn)
Route 21 - Mr. Mime, Tangela (all times)
Route 28 - Dodrio, Ponyta, Rapidash (Morn, Day), Sneasel, Quagsire (Night), Ursaring OR Donphan (all times)
(Raikou, Entei and Suicune roam across Johto after being awakened at Burned Tower)

Cave or unknown building:
Dark Cave (Violet side) - Zubat, Geodude, Dunsparce (requires Rock Smash)
Sprout Tower - Rattata (Morn, Day), Gastly (Night)
Ruins of Alph - Unown
Union Cave - Zubat, Geodude, Sandshrew (Gold only), Onix
Slowpoke Well - Zubat, Slowpoke
Ilex Forest - Paras (Morn, Day), Oddish (Night), Caterpie OR Weedle, Metapod OR Kakuna (all times)
Burned Tower - Rattata (Morn, Day), Gastly (Night), Koffing, Magmar (all times)
Mt. Mortar - Zubat, Machop, Marill (during swarm only)
Union Cave (B1F, requires Surf) - Zubat, Golbat, Geodude, Sandshrew (Gold only), Sandslash (Gold only), Onix
Ice Path - Zubat, Golbat, Swinub, Jynx, Delibird (Silver only)
Dark Cave (Blackthorn side) - Zubat, Golbat, Wobbuffet
Tin Tower - Rattata, Raticate (Morn, Day), Gastly, Haunter (Night), Ho-oh (only one)
Whirl Islands - Zubat, Golbat, Seel, Krabby, Lugia (only one)
Tohjo Falls - Zubat, Golbat, Slowpoke
Victory Road - Golbat, Machoke, Graveler, Rhyhorn, Onix, Ursaring OR Donphan
Mt. Moon - Zubat, Geodude, Clefairy, Sandshrew (Gold only)
Diglett's Cave - Diglett, Dugtrio
Rock Tunnel - Zubat, Machop, Geodude, Onix, Cubone, Kangaskhan
Silver Cave (1F) - Golbat, Machoke, Graveler, Onix, Ursaring OR Donphan, Larvitar
Silver Cave (2F) - Golbat, Onix, Golduck, Quagsire, Ursaring OR Donphan, Misdreavus

Surfing:
Routes 10, 12, 19, 20, 21, 26, 27, 34, 40 and 41, Vermilion City, Cinnabar Island, New Bark Town, Cherrygrove City, Olivine City, Cianwood City - Tentacool, Tentacruel
Routes 6, 23, 24, 25, 35, 42 and 44, Viridian City, Cerulean City, Celadon City, Violet City, Ecruteak City - Psyduck
Route 32, Union Cave, Ruins of Alph - Wooper
Union Cave (B1F) - Wooper, Quagsire, Lapras (every Friday)
Mt. Mortar - Zubat, Marill
Lake of Rage - Magikarp, Gyarados
Dragon's Den - Magikarp, Dratini
Slowpoke Well, Tohjo Falls - Zubat, Golbat, Slowpoke
Silver Cave - Golbat, Golduck

Fishing:
Old Rod:
Routes 10, 12, 19, 20, 21, 26, 27, 32, 34, 40 and 41, Vermilion City, Cinnabar Island, New Bark Town, Cherrygrove City, Olivine City, Cianwood City - Magikarp, Tentacool
Routes 6, 23, 24, 25, 28, 30, 31, 35 and 44, Viridian City, Cerulean City, Celadon City, Violet City, Ecruteak City - Magikarp, Poliwag
Routes 42 and 45, Mt. Mortar, Dark Cave, Whirl Islands, Tohjo Falls, Silver Cave - Magikarp, Goldeen
Dragon's Den, Lake of Rage - Magikarp
Good Rod:
Routes 10, 12, 19, 20, 21, 26, 27, 32, 34, 40 and 41, Vermilion City, Cinnabar Island, New Bark Town, Cherrygrove City, Olivine City, Cianwood City - Tentacool, Krabby
Routes 6, 23, 24, 25, 28, 30, 31, 35 and 44, Viridian City, Cerulean City, Celadon City, Violet City, Ecruteak City - Poliwag
Routes 42 and 45, Mt. Mortar, Dark Cave, Whirl Islands, Tohjo Falls, Silver Cave - Goldeen
Dragon's Den, Lake of Rage - Magikarp
Super Rod:
Routes 10, 19, 20, 21, 26, 27, 34, 40 and 41, Cinnabar Island, New Bark Town, Cherrygrove City, Cianwood City - Krabby, Kingler
Routes 6, 23, 24, 25, 28, 35 and 44, Viridian City, Cerulean City, Celadon City, Violet City, Ecruteak City - Poliwag, Poliwhirl
Vermilion City, Olivine City - Shellder, Corsola (Morn, Day), Staryu, Chinchou (Night)
Routes 12 and 32 - Qwilfish
Routes 42 and 44 - Remoraid
Route 45 - Mt. Mortar, Dark Cave, Tohjo Falls, Silver Cave - Goldeen, Seaking
Dragon's Den, Lake of Rage - Gyarados
Whirl Islands - Horsea, Seadra

Headbutt:
Routes 3, 4, 28, 31, 42, 44, 45 ad 46 - Spearow, Aipom, Heracross
All other routes with Headbutt trees - Caterpie OR Weedle, Metapod OR Kakuna, Butterfree OR Beedrill, Exeggcute, Pineco

Non-encountered Pokémon:
Chikorita OR Cyndaquil OR Totodile - Starter
Togepi - Hatched from Egg given by Professor Elm's aide
Tyrogue - Prize for defeating Black Belt in Mt. Mortar
Porygon - Game Corner Prize (9999 coins)
Shuckle - Obtain from Cianwood resident

Hoenn (RSE)
Tall grass:
Route 101 - Poochyena, Zigzagoon, Wurmple
Route 103 - Poochyena, Zigzagoon, Wingull
Route 102 - Poochyena, Seedot OR Lotad (both in Emerald), Ralts, Surskit (during swarm only, not in Emerald)
Route 104 - Zigzagoon, Wurmple, Taillow, Wingull
Petalburg Woods - Wurmple, Silcoon, Cascoon, Shroomish, Slakoth
Route 116 - Zigzagoon, Taillow, Nincada, Whismur, Skitty (during swarm only)
Route 110 - Oddish, Wingull, Minun OR Plusle (both in Emerald), Electrike, Gulpin
Route 117 - Oddish, Marill, Roselia (not in Emerald), Illumise OR Volbeat (both in Emerald)
Route 112 - Numel, Machop
Route 113 - Spinda, Skarmory, Sandshrew (not in Emerald), Slugma (Emerald only)
Route 114 - Seedot OR Lotad (both in Emerald), Nuzleaf OR Lombre (both in Emerald), Zangoose (R) OR Seviper (SE), Swablu
Jagged Pass - Numel, Spoink
Route 115 - Taillow, Swellow, Swablu, Jigglypuff
Route 118 - Zigzagoon, Linoone, Electrike, Manectric, Kecleon (rare)
Route 119 - Zigzagoon, Linoone, Oddish, Tropius, Kecleon (rare)
Route 120 - Zigzagoon, Linoone, Oddish, Marill, Absol, Kecleon (rare)
Routes 121 and 123 -  Zigzagoon, Linoone, Duskull OR Shuppet (both in Emerald), Kecleon (rare)
Mt. Pyre - Wingull, Vulpix, Meditite (not in Emerald), Duskull OR Shuppet (both in Emerald), Chimecho (rare)
Safari Zone Area 1 - Oddish, Gloom, Girafarig, Pikachu
Safari Zone Area 2 - Doduo, Natu, Girafarig, Wobbuffet
Safari Zone Area 3 (requires Mach Bike) - Doduo, Dodrio, Rhyhorn, Pinsir
Safari Zone Area 4 (requires Acro Bike) - Natu, Xatu, Phanpy, Heracross
Safari Zone Area 5 (Emerald only) - Pineco, Houndour, Miltank, Gligar
Safari Zone Area 6 (Emerald only) - Mareep, Aipom, Snubbull, Stantler

Cave or unknown building:
Rusturf Tunnel - Whismur
Granite Cave (1F) - Zubat, Geodude, Makuhita, Abra
Granite Cave (B1F, B2F) - Zubat, Aron, Mawile OR Sableye (both in Emerald)
Fiery Path - Slugma, Torkoal, Koffing OR Grimer
Meteor Falls - Zubat, Solrock (RE) OR Lunatone (S)
Meteor Falls (back, requires Waterfall) - Golbat, Solrock (RE) OR Lunatone (S), Bagon
Route 111 Desert - Sandshrew, Trapinch, Cacnea, Baltoy
New Mauville - Magnemite, Voltorb
Mt. Pyre - Duskull OR Shuppet (both in Emerald)
Shoal Cave - Zubat, Golbat, Spheal, Snorunt (B1F only)
Seafloor Cavern - Zubat, Golbat
Cave of Origin - Zubat, Golbat, Mawile OR Sableye (both in Emerald)
Victory Road (1F) - Zubat, Golbat, Whismur, Loudred, Makuhita, Hariyama
Victory Road (2F) - Zubat, Golbat, Aron, Lairon, Meditite (not in Emerald), Medicham (not in Emerald), Mawile OR Sableye (both in Emerald)
Sky Pillar - Golbat, Mawile OR Sableye (both in Emerald), Dusclops (R) OR Banette (SE), Claydol, Altaria
Magma Hideout (Emerald only) - Geodude, Graveler, Torkoal
Artisan Cave (Emerald only) - Smeargle

Surfing:
Routes 103, 104 South, 105-110, 118, 122 and 124-134, Slateport City, Lilycove City, Mossdeep City, Pacifidlog Town, Ever Grande City - Wingull, Pelipper, Tentacool
Route 119 - Tentacool
Routes 102, 104 North, 111, 114, 117, 120 and 123, Petalburg City - Marill
Meteor Falls - Zubat, Golbat, Solrock (RE) OR Lunatone (S)
Shoal Cave - Zubat, Golbat, Spheal
Seafloor Cavern, Victory Road - Zubat, Golbat
Safari Zone - Psyduck
Sootopolis City - Magikarp, Gyarados

Fishing:
Old Rod:
Routes 103, 104 South, 105-110, 118-122 and 124-134, Slateport City, Lilycove City, Mossdeep City, Pacifidlog Town, Ever Grande City - Magikarp, Tentacool
Routes 102, 104 North, 114 and 117, Petalburg City, Safari Zone - Magikarp, Goldeen
All other waters - Magikarp
Good Rod:
Routes 103, 104 South, 105-110, 122 and 124-134, Slateport City, Lilycove City, Mossdeep City, Pacifidlog Town, Ever Grande City - Tentacool, Wailmer
Routes 102 and 117, Petalburg City - Goldeen, Corphish
Route 104 North, Safari Zone - Goldeen
Routes 111 and 114, Meteor Falls, Shoal Cave, Seafloor Cavern, Victory Road - Magikarp, Barboach
Routes 118 and 119 - Tentacool, Carvanha
Sootopolis City - Magikarp
Super Rod:
Routes 103, 104 South, 105-110, 122 - Wailmer
Routes 124-131, Mossdeep City - Sharpedo, Wailmer
Lilycove City - Staryu, Wailmer
Ever Grande City - Luvdisc, Wailmer
Routes 132, 133 and 134 - Horsea, Wailmer
Pacifidlog Town - Corsola, Wailmer
Routes 102 and 117, Petalburg City - Corphish
Route 118 - Carvanha
Route 119 - Carvanha, Feebas
Route 104 North - Goldeen
Routes 111 and 114, Meteor Falls, Shoal Cave, Seafloor Cavern, Victory Road - Barboach, Whiscash
Safari Zone - Goldeen, Seaking
Sootopolis City - Magikarp, Gyarados

Rock Smash:
Granite Cave - Nosepass
Safari Zone Area 5 (Emerald only) - Shuckle

Diving:
Underwater areas with seaweed - Chinchou, Clamperl, Relicanth

Single encounters:
Regirock - Desert Ruins
Regice - Island Cave
Registeel - Ancient Tomb
Latias OR Latios - Roaming across Hoenn after Elite Four
Groudon - Cave of Origin (R), Terra Cave (E)
Kyogre - Cave of Origin (S), Marina Cave (E)
Rayquaza - Sky Pillar
Sudowoodo - Use Wailmer Pail on tree at Battle Frontier (Emerald only)

Non-encountered Pokémon:
Treecko OR Torchic OR Mudkip - Starter
Lileep - Trade Root Fossil in Devon Corporation 2F
Anorith - Trade Claw Fossil in Devon Corporation 2F
Castform - obtain from Weather Institute
Beldum - obtain from Steven's house after defeating him (RS)/Wallace (E)
Chikorita OR Cyndaquil OR Totodile - Starter (Emerald only, after completing regional Pokédex)

Sevii Islands (FRLG)
Tall grass:
Kindle Road - Ponyta, Spearow, Fearow, Doduo, Dodrio
Mt. Ember - Ponyta, Rapidash, Fearow, Magmar (LG only)
Treasure Beach - Tangela
Cape Brink - Pidgey, Pidgeotto, Mankey OR Meowth, Primeape OR Persian
Bond Bridge - Pidgey, Pidgeotto, Oddish OR Bellsprout, Gloom OR Weepinbell
Berry Forest - Oddish OR Bellsprout, Gloom OR Weepinbell, Drowzee, Hypno, Venonat, Venomoth
Three Isle Port - Dunsparce
Five Isle Meadow - Pidgey, Pidgeotto, Sentret, Hoppip
Water Path - Oddish OR Bellsprout, Gloom OR Weepinbell, Psyduck OR Slowpoke, Mareep
Pattern Bush - Caterpie, Weedle, Metapod OR Kakuna, Spinarak OR Ledyba, Sunkern
Ruin Valley - Spearow, Fearow, Hoothoot, Teddiursa, Yanma
Canyon Entrance - Spearow, Fearow, Mankey OR Meowth, Primeape OR Persian, Aipom
Sevault Canyon - Cubone, Marowak, Geodude, Graveler, Onix, Gligar (FR only), Larvitar

Cave or unknown building:
Mt. Ember - Machop, Machoke, Geodude, Graveler
Icefall Cave - Zubat, Golbat, Seel, Dewgong, Swinub, Delibird OR Sneasel
Lost Cave - Zubat, Golbat, Gastly, Haunter, Murkrow OR Misdreavus
Tanoby Chambers - Unown

Surfing:
Four Island Ponds, Ruin Valley - Wooper
Five Island (throughout) - Tentacool, Tentacruel, Hoppip
Tanoby Ruins - Tentacool, Tentacruel, Mantine (LG only)
All other surfable waters - Tentacool, Tentacruel

Fishing:
Old Rod (all waters) - Magikarp
Good Rod:
Cape Brink, Four Island Ponds - Magikarp, Poliwag
All other waters - Magikarp, Tentacool
Super Rod:
Cape Brink, Four Island Ponds - Poliwag, Poliwhirl
All other waters - Horsea OR Krabby, Qwilfish OR Remoraid

Non-encountered Pokémon:
Togepi - hatch from Egg received in Water Labyrinth

Sinnoh (DPPt)
Tall grass:
Route 201 - Starly, Bidoof, Poochyena (during swarm only)
Lake Verity - Starly, Bidoof, Surskit (during swarm only)
Route 202 - Starly (Day), Kricketot (Morning, Night), Bidoof (all times)
Route 203 - Kricketot (Morning, Night), Abra, Shinx (Morning, Day), Zubat (Night), Ralts (Platinum - all times)
Route 204 - Budew (Day), Kricketot (Morning, Night), Shinx (Morning, Day), Zubat (Night),  Bidoof (all times)
Route 207 - Geodude, Machop, Aron (during swarm only)
Route 205 - Buizel, Shellos
Valley Windworks - Buizel, Pachirisu, Elekid (Platinum only), Electrike (during swarm only)
Eterna Forest - Buneary (Morn, Day), Murkrow OR Misdreavus (Night), Wurmple, Silcoon OR Cascoon (all times), Slakoth (during swarm only)
Routes 208 and 211 West - Ponyta, Bidoof (Morn, Day), Chingling, Hoothoot (Night)
Route 206 - Stunky OR Bonsly, Geodude, Kricketune, Baltoy (during swarm only)
Route 209 - Starly, Staravia, Bibarel, Mime. Jr OR Glameow
Route 210 South - Stunky OR Bonsly, Ponyta, Geodude, Chansey, Swablu (Platinum only)
Route 215 - Abra, Kadabra, Azurill, Geodude, Scyther (Platinum only), Kecleon (during swarm only)
Route 214 - Bibarel, Geodude, Girafarig, Rhyhorn (Platinum only)
Route 213 - Wingull, Buizel, Shellos, Magnemite (Platinum only)
Route 212 - Marill (Morn, Day), Wooper (Night), Bibarel (DP only - all times), Tropius (Platinum only - all times)
Trophy Garden - Pichu, Pikachu, Roselia, Kricketune
Trophy Garden (post-credits, species change every day) Plusle, Minun, Castform, Bonsly, Mime. Jr, Happiny
Great Marsh Area 1 - Bidoof, Bibarel, Croagunk, Shroomish (requires National Dex)
Great Marsh Area 2 - Azurill, Marill (Morn, Day), Hoothoot, Noctowl (Night), Skorupi
Great Marsh Area 3 - Wooper, Quagsire, Carnivine, Tangela (Platinum only)
Great Marsh Area 4 - Budew, Roselia, Psyduck, Yanma (Platinum only)
Route 210 North and 211 East - Machop, Machoke, Bibarel (Morn, Day), Hoothoot, Noctowl, Chingling (Night), Gligar (Platinum only - all times)
Route 218 - Mr. Mime OR Glameow, Chatot, Shellos, Gastrodon, Staravia, Lickitung (Platinum only)
Route 221 - Skuntank OR Sudowoodo, Shellos, Gastrodon, Absol (Platinum only)
Fuego Ironworks - Shinx, Luxio, Pachirisu, Magby (Platinum only - Morn, Day), Houndour (Platinum only - Night)
Route 216 - Snover, Machoke, Graveler, Meditite (Morn, Day), Sneasel (Night), Swinub (Platinum only)
Route 217, Acuity Lakefront - Snover, Machoke, Graveler, Meditite (Morn, Day), Sneasel (Night), Snorunt (Platinum only)
Lake Acuity - Bibarel (Morn, Day), Sneasel (Night), Psyduck, Golduck (all times)
Mt. Coronet - Machoke, Medicham (Morn, Day), Golbat, Noctowl (Night), Snover, Abomasnow (all times), Loudred (during swarm only)
Route 222 - Mr. Mime OR Purugly, Chatot, Gastrodon, Floatzel, Magneton (Platinum only), Skitty (during swarm only)
Route 224 - Weepinbell (Morn, Day), Gloom (Night), Floatzel, Gastrodon, Lotad, Lombre (all times)
Route 225 - Makuhita, Hariyama (Morn, Day), Shuppet, Banette (Night), Zangoose OR Seviper (all times)
Route 226 - Zigzagoon, Linoone, Taillow, Swellow
Route 227 - Numel, Camerupt, Spoink, Grumpig, Spinda
Route 228 - Trapinch, Vibrava, Cacnea, Cacturne, Nincada, Beldum (during swarm only)
Route 229 - Ledian (Morn), Ariados (Night), Seedot, Nuzleaf, Gulpin, Swalot
Route 230 - Beautifly, Dustox, Volbeat OR Illumise

Cave or unknown building:
Ravaged Path - Zubat
Oreburgh Gate (1F) - Zubat, Geodude
Oreburgh Gate (B1F, requires Rock Smash) - Zubat, Geodude, Psyduck
Oreburgh Mine - Zubat, Geodude, Onix
Old Chateau - Gastly
Wayward Cave - Zubat, Bronzor, Nosepass (Platinum only)
Wayward Cave (secret room) - Zubat, Bronzor, Gible
Mt. Coronet (1F) - Zubat, Geodude, Bronzor, Cleffa, Chingling
Mt. Coronet (B1F) - Zubat, Golbat, Bronzor, Clefairy, Chingling
Mt. Coronet (upper floors) - Zubat, Golbat, Bronzor, Bronzong, Clefairy, Chingling, Chimecho
Lost Tower - Gastly, Haunter, Duskull (Platinum only)
Solaceon Ruins - Unown
Ruin Maniac's Cave - Geodude, Hippopotas
Iron Island - Zubat, Golbat, Geodude, Graveler, Machop, Machoke, Onix
Victory Road - Golbat, Machoke, Kadabra, Graveler, Onix, Floatzel (B2F)
Snowpoint Temple - Golbat, Onix, Sneasel, Mawile OR Sableye
Stark Mountain - Golbat, Koffing, Weezing, Slugma, Magcargo, Numel, Camerupt, Torkoal

Surfing:
Routes 218-221 - Wingull, Tentacool
Route 222, Sunyshore City - Pelipper, Tentacruel
Routes 223 and 224 - Pelipper, Tentacruel, Mantyke
Routes 226, 229 and 230 - Pelipper, Tentacruel, Spheal, Sealeo
Routes 203, 205, 208, 209, Celestic Town - Psyduck, Golduck
Mt. Coronet - Zubat, Golbat
Victory Road - Golbat, Floatzel, Lapras (after obtaining National Dex)

Fishing:
Old Rod (all waters) - Magikarp
Good Rod:
Route 218 - Magikarp, Tentacool, Finneon
Routes 219-221 - Magikarp, Tentacool
Routes 222-224, Sunyshore City - Magikarp, Finneon, Remoraid
Iron Island, Great Marsh, Mt. Coronet, Victory Road - Magikarp, Barboach
Mt. Coronet - Magikarp, Barboach, Feebas (selected tiles only)
Routes 226, 229 and 230 - Magikarp, Tentacool, Wailmer
Routes 203, 205, 208, 209, Celestic Town - Magikarp, Goldeen
Super Rod:
Route 218 - Gyarados, Lumineon
Routes 219-221 - Gyarados, Horsea
Routes 222-224 - Gyarados, Lumineon, Octillery
Pokémon League - Gyarados, Lumineon, Octillery, Luvdisc
Route 226 - Chinchou, Clamperl, Relicanth
Routes 229 and 230 - Gyarados, Sharpedo, Wailmer
Iron Island - Qwilfish
Great Marsh - Carvanha, Whiscash
Routes 203, 205, 208, 209 - Gyarados, Seaking
Celestic Town - Corphish, Seaking
Sunyshore City - Gyarados, Staryu

Honey trees:
Most trees - Wurmple, Silcoon OR Cascoon, Burmy, Combee, Cherubi, Aipom, Heracross
Selected trees only - Munchlax

Single encounters:
Drifloon - Valley Windworks (every Friday)
Spiritomb - Route 209 (after connecting with others over Wi-Fi 32 times)
Uxie - Acuity Cavern
Mesprit - Roaming across Sinnoh after interaction at Verity Cavern
Azelf - Valor Cavern
Dialga OR Palkia - Spear Pillar
Giratina - Distortion World (Platinum), Turnback Cave (DP)
Heatran - Stark Mountain
Regigigas - Snowpoint Temple (requires Regirock, Regice and Registeel)
Cresselia - Roaming across Sinnoh after interaction at Fullmoon Island

Non-encountered Pokémon:
Turtwig OR Chimchar OR Piplup - Starter
Eevee - obtain from Bebe
Riolu - hatch from Egg obtained by Riley
Porygon - obtain from traveller at Veilstone City (Platinum only)
Cranidos - Trade Skull Fossil at Oreburgh Mining Museum
Shieldon - Trade Armor Fossil at Oreburgh Mining Museum

Unova (BW)
Tall grass:
Route 1 - Patrat, Lillipup
Route 2 - Pidove, Purrloin
Dreamyard - Patrat, Purrloin, Munna
Route 3 - Pidove, Blitzle, Audino (rustling grass only)
Pinwheel Forest (outside) - Tympole, Timburr, Throh OR Sawk
Pinwheel Forest (inside) - Sewaddle, Venipede, Cottonee OR Petilil
Route 5 - Minccino, Trubbish, Gothita OR Solosis
Cold Storage - Herdier, Minccino, Vanillite
Route 6 - Deerling, Karrablast, Emolga (rustling grass only)
Route 7 - Tranquill, Foongus, Cubchoo
Route 9 - Liepard, Pawniard, Gothorita OR Duosion
Route 10 - Bouffalant, Throh OR Sawk, Rufflet OR Vullaby
Route 18 - Scraggy, Dwebble, Watchog

Cave or unknown building:
Wellspring Cave - Roggenrola, Woobat, Drilbur (dust clouds only)
Route 4 (Desert) - Sandile, Dwebble, Maractus
Desert Resort - Dwebble, Darumaka, Scraggy
Relic Castle - Yamask, Sigilyph
Driftveil Drawbridge - Ducklett (shadow only)
Chargestone Cave - Joltik, Ferroseed, Klink, Tynamo
Celestial Tower - Elgyem, Litwick
Twist Mountain - Boldore, Gurdurr, Cryogonal
Mistralton Cave - Boldore, Woobat, Axew
Dragonspiral Tower - Golett, Mienfoo, Druddigon
Victory Road (Outside) - Mienfoo, Heatmor, Rufflet OR Vullaby
Victory Road (Inside) - Mienfoo, Durant, Deino

Puddle:
Route 8, Icirrus City, Moor of Icirrus - Palpitoad, Shelmet, Stunfisk

Single encounters:
Tornadus OR Thundurus - Roaming across Unova after talking to an NPC in Route 7
Cobalion - Mistralton Cave Guidance Chamber
Terrakion - Victory Road Trial Chamber
Virizion - Pinwheel Forest Rumination Field
Reshiram OR Zekrom - N's Castle
Landorus - Abundant Shrine (requires Tornadus and Thundurus)
Kyurem - Giant Chasmn

Non-encountered Pokémon:
Snivy OR Tepig OR Oshawott - Starter
Pansage OR Pansear OR Panpour - obtain from traveller at Dreamyard
Zorua - obtain from Rood at Driftveil City (B2W2 only)
Archen - Trade Plume Fossil at Nacrene Museum
Tirtouga - Trade Cover Fossil at Nacrene Museum
Larvesta - Hatch egg obtained at Route 18
 */