$(document).ready(function(){
	
	//get the library persistent id from url
	function GetURLParameter(param){
		var pageURL = window.location.search.substring(1);
		var urlVariables = pageURL.split('&');
		for (var i = 0; i < urlVariables.length; i++){
			var parameterName = urlVariables[i].split('=');
			if (parameterName[0] == param){
				return parameterName[1];
			}
		}
	}
	
	var lpid = GetURLParameter('id');
	
	//waiting icon
	var waiting = '<i class="fa fa-spinner fa-pulse fa-3x fa-fw" style="color:cornflowerblue"></i>';
	var saveIcon = '<i class="fa fa-fw fa-save"></i>';
	var deleteIcon = '<i class="fa fa-fw fa-times-circle"></i>';
	
	//clear the songs and playlists
	$("#songs").empty();
	$("#playlists").empty();
	
	//append the waiting icon
	$("#songs").append(waiting);
	$("#playlists").append(waiting);
	
	$.when(
	
		//--- Display All Songs ---//
		//get all of the songs
		$.ajax({
			
			type:"GET",
			url:"rest/base/getsongsbylpid",
			data: {lpid: lpid},
			cache: false,
			dataType:"json",
			
			success: function(data){
				
				var songTable = '<div class="table-responsive">'+'<table class="table table-bordered" width="100%" id="songTable" cellspacing="0">'
					+'<thead><tr><th>ID</th><th id="songTitle">Title</th><th id="songArtist">Artist</th><th id="songAlbum">Album</th><th>Save</th><th>Delete</th></tr></thead>'
					+'<tfoot><tr><th>ID</th><th>Title</th><th>Artist</th><th>Album</th><th>Save</th><th>Delete</th></tr></tfoot>'
					+'<tbody>';
				
				$.each(data.songList, function(index, value){
					var newLine = '<tr><td>'+value.song_id+'</td><td id="songTitle" class="unclicked">'+value.song_name+'</td><td id="songArtist" class="unclicked">'+value.artist+'</td><td id="songAlbum" class="unclicked">'+value.album+'</td><td id="saveSong">'+saveIcon+'</td><td id="deleteSong">'+deleteIcon+'</td></tr>';
					songTable+=newLine;
				
				});

				songTable+='</tbody></table></div>';
				
				$("#songs").empty();
				$("#songs").append(songTable);
				$('#songTable').dataTable(
					{
					"columnDefs": [
						{
						"targets": [ 0 ],
						"visible": false,
						"searchable": false
						}
					]
					}
				);
				
				//function on click
				var songTable = $('#songTable').DataTable();
				
				//make fields editable on click
				$('#songTable tbody').on('click', '#songTitle', function () {
					var cellClass = this.className;
					if(cellClass === "unclicked"){
						var cell = songTable.cell(this);
						
						var data = songTable.row(this).data();
						var songTitle = data[1];
						var editTitle="<input type='text' value='"+songTitle+"'>";
						cell.data(editTitle).draw();
						
						this.className = "clicked";
					}	
				});	
				
				$('#songTable tbody').on('click', '#songArtist', function () {
					var cellClass = this.className;
					if(cellClass === "unclicked"){
						var cell = songTable.cell(this);
						
						var data = songTable.row(this).data();
						var songArtist = data[2];
						var editArtist="<input type='text' value='"+songArtist+"'>";
						cell.data(editArtist).draw();
						
						this.className = "clicked";
					}	
				});	
				
				$('#songTable tbody').on('click', '#songAlbum', function () {
					var cellClass = this.className;
					if(cellClass === "unclicked"){
						var cell = songTable.cell(this);
						
						var data = songTable.row(this).data();
						var songAlbum = data[3];
						var editAlbum="<input type='text' value='"+songAlbum+"'>";
						cell.data(editAlbum).draw();
						
						this.className = "clicked";
					}	
				});	
				
				//delete song from db
				$('#songTable tbody').on('click', '#deleteSong', function () {
					var data = songTable.row(this).data();
					alert( 'You are deleting '+data[1]+'\'s row' );
					var song_id = data[0];
					
					$.ajax({
				
						type:"DELETE",
						url:"rest/base/deletesong",
						data: {song_id: song_id},
						cache: false,
						dataType:"json",
						
						//delete rows from data table
						success: function(data){
							var indexes = songTable.rows().eq(0).filter(function (rowIndex) {
								return songTable.cell(rowIndex, 0).data() === song_id ? true : false;
							});
								
							songTable
								.rows(indexes)
								.remove()
								.draw();
						}	
					});
				});	
				
				//save
				$('#songTable tbody').on('click', '#saveSong', function () {
					
					var data = songTable.row(this).data();
					alert( 'You are saving '+data[1]+'\'s row' );
				});
			}
		}),
		
		//--- Display All Playlists ---//
		//get all of the playlists
		$.ajax({
			
			type:"GET",
			url:"rest/base/getplaylistsbylpid",
			data: {lpid: lpid},
			cache: false,
			dataType:"json",
			
			success: function(data){
				
				var playlistTable = '<div class="table-responsive">'+'<table class="table table-bordered" width="100%" id="playlistTable" cellspacing="0">'
					+'<thead><tr><th>ID</th><th>Playlist</th><th>Title</th><th>Artist</th><th>Album</th><th>Save</th></tr></thead>'
					+'<tfoot><tr><th>ID</th><th>Playlist</th><th>Title</th><th>Artist</th><th>Album</th><th>Save</th></tr></tfoot>'
					+'<tbody>';
				
				$.each(data.playlistList, function(index, value){
					
					var playlistName = value.playlist_name;
					var playlistId = value.playlist_id;
					
					$.each(value.playlistsongs, function(index, value){
						var newLine = '<tr><td>'+playlistId+'</td><td id="deletePlaylist">'+deleteIcon+playlistName+'</td><td>'+value.song_name+'</td><td>'+value.artist+'</td><td>'+value.album+'</td><td id="savePlaylist">'+saveIcon+'</td></tr>';
						playlistTable+=newLine;
					});					
				
				});

				playlistTable+='</tbody></table></div>';
				
				$("#playlists").empty();
				$("#playlists").append(playlistTable);
				$('#playlistTable').dataTable(
					{
					"columnDefs": [
						{
						"targets": [ 0 ],
						"visible": false,
						"searchable": false
						}
					]
					}
				);
				
				//function on click
				var playlistTable = $('#playlistTable').DataTable();
				
				//delete
				$('#playlistTable tbody').on('click', '#deletePlaylist', function () {
					var data = playlistTable.row(this).data();
					alert( 'You are deleting '+data[1]+'\'s row' );
					
					var playlist_id = data[0];
					
					//delete playlist from db
					$.ajax({
			
						type:"DELETE",
						url:"rest/base/deleteplaylist",
						data: {playlist_id: playlist_id},
						cache: false,
						dataType:"json",
						
						//delete rows from data table
						success: function(data){
							
							var indexes = playlistTable.rows().eq(0).filter(function (rowIndex) {
								return playlistTable.cell(rowIndex, 0).data() === playlist_id ? true : false;
							});
								
							playlistTable
								.rows(indexes)
								.remove()
								.draw();
						}	
					});
				});	
				
				//save
				$('#playlistTable tbody').on('click', '#savePlaylist', function () {
					var data = playlistTable.row( this ).data();
					alert( 'You are saving '+data[1]+'\'s row' );
				});
			}	
		})
	
	).then(function(){
		
	});
	
});	