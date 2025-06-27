package com.example.musicapp.core

import com.example.musicapp.core.room.entities.album.Album
import com.example.musicapp.core.room.entities.artist.Artist
import com.example.musicapp.core.room.entities.song.Song

val sampleArtists = listOf(
    Artist(id = "1", name = "Weekend", imageUrl = "https://plus.unsplash.com/premium_photo-1669047670905-fa4331d07e06?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    Artist(id = "2", name = "Ed sheeran", imageUrl = "https://plus.unsplash.com/premium_photo-1669047670905-fa4331d07e06?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    Artist(id = "3", name = "Drake", imageUrl = "https://plus.unsplash.com/premium_photo-1669047670905-fa4331d07e06?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
    Artist(id = "4", name = "Justin Beiber", imageUrl = "https://plus.unsplash.com/premium_photo-1669047670905-fa4331d07e06?q=80&w=2940&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
)

val sampleAlbums = listOf(
    Album(albumId = "1", title = "StarBoy", songIds = arrayListOf("1"), imageUrl = "https://i.redd.it/w86hly5tslx71.jpg", releasedData = 444104400000, artistId = "1", bgColor = "#FF0000"),
    Album(albumId = "2", title = "X",songIds = arrayListOf("2"), imageUrl = "https://cdn.hmv.com/r/w-640/hmv/files/0a/0a192bc5-2702-495f-9a62-9097f74708b5.jpg", releasedData = 347406000000, artistId = "2", bgColor = "#90EE90"),
    Album(albumId = "3", title = "Scorpion",songIds = arrayListOf("3"), imageUrl = "https://upload.wikimedia.org/wikipedia/en/9/90/Scorpion_by_Drake.jpg", releasedData = 347406000000, artistId = "3", bgColor = "#232b2b"),
    Album(albumId = "4", title = "Purpose",songIds = arrayListOf("4"), imageUrl = "https://upload.wikimedia.org/wikipedia/en/2/27/Justin_Bieber_-_Purpose_%28Official_Album_Cover%29.png", releasedData = 347406000000, artistId = "3", bgColor = "#bbbbbb"),
    Album(albumId = "5", title = "Black Panther",songIds = arrayListOf("6"),  imageUrl = "https://i.pinimg.com/474x/08/e3/23/08e32318d9d75b583b4745c712abf67a.jpg", releasedData = 347406000000, artistId = "2", bgColor = "#232b2b"),
    Album(albumId = "6", title = "Lover",songIds = arrayListOf("5"),  imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMHAj7Sy3LaSIfYEIFOv-yb4vLu5dKElA_Yw&s", releasedData = 347406000000, artistId = "1", bgColor = "#FFC0CB"),
    Album(albumId = "7", title = "Indigo",songIds = arrayListOf("1", "2", "3", "4", "5", "6"), imageUrl = "https://upload.wikimedia.org/wikipedia/en/1/1f/Chris_Brown_-_Indigo.png", releasedData = 347406000000, artistId = "1", bgColor = "#8B0000"),
    )

val sampleSongs = listOf(
    Song(id = "1", name = "Sunflower", albumId = "1", artistId = "1", imageUrl = "https://static.stereogum.com/uploads/2018/10/DprBhEZVAAAn-Dh-1539880305.jpg", songUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", releasedData = 448717200000, ranking = 1, bgColor = "#FFC0CB"),
    Song(id = "2", name = "Shape of you",albumId = "2", artistId = "1", songUrl = "https://www.cdnpagal.top/files/sfd41/20462/Waka%20Waka_320(PagalWorld.com.so).mp3",imageUrl = "https://upload.wikimedia.org/wikipedia/en/b/b4/Shape_Of_You_%28Official_Single_Cover%29_by_Ed_Sheeran.png", releasedData = 448717200000, ranking = 2, bgColor = "#FFC0CB"),
    Song(id = "3", name = "Till I Collapse",albumId = "3", artistId = "2", songUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6SdWRZIOFknHcHfEPjXpptfwtcJvH-4UjTQ&s", releasedData = 348060600000, ranking = 3, bgColor = "#FFC0CB"),
    Song(id = "4", name = "Eastside", albumId = "4", artistId = "2", songUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", imageUrl = "https://i.pinimg.com/564x/b4/d4/47/b4d447927bbb68ee4e2d92c08135b68b.jpg", releasedData = 348060600000, ranking = 4, bgColor = "#FFC0CB"),
    Song(id = "5", name = "Born to die", albumId = "5", artistId = "2", songUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfx9Ey-z1qZwebum87uFZ_fGW1WFDA7stjxA&s", releasedData = 348060600000, ranking = 4, bgColor = "#FFC0CB"),
    Song(id = "6", name = "Sunshine",albumId = "6", artistId = "2", songUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQe8d6NKMPkODSCylnYt4bidxN6hTRS7YSUkg&s", releasedData = 348060600000, ranking = 5, bgColor = "#FFC0CB")
)