CREATE TABLE User (
	Username		VARCHAR(20) PRIMARY KEY,
	Password		VARCHAR(20) NOT NULL
);

CREATE TABLE Paths (
	Username		VARCHAR(20),
	Path			VARCHAR(500),
	LastSynced		TIMESTAMP,
	TimerLength		DATETIME, -- Not sure what data type to use here
	CONSTRAINT pk_PathID PRIMARY KEY (Username, Path),
	FOREIGN KEY (Username) REFERENCES User (Username)
);

CREATE TABLE File (
	Path			VARCHAR(500) PRIMARY KEY,
	Filename		VARCHAR(100) NOT NULL,
	--Type			ENUM('A','I','M','T','O') DEFAULT 'O', -- Audio, Image, Movie, TV, Other
	Type 			CHAR(1) CHECK Type IN ('A','S','I','M','T','V','O'), -- Audio, Image, Movie, TV, Other
	Filesize 		VARCHAR(30),
	ActualExtension	VARCHAR(6),		-- Reduce to 5 if we remove the . first
	FileExtension	VARCHAR(5)
);

CREATE TABLE FileUpdates ( -- Named LastUpdated on EER
	Path			VARCHAR(500) PRIMARY KEY,
	Permissions		VARCHAR(12),
	AccessDateTime		TIMESTAMP,
	ModifyDateTime		TIMESTAMP,
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE Owns (
	Path			VARCHAR(500),
	Username		VARCHAR(20),
	PlayCount		INTEGER,
	Notes			VARCHAR(1000),
	WatchList		BOOLEAN,
	Favorite		BOOLEAN,
	Rating			INTEGER,
	CONSTRAINT pk_OwnsID PRIMARY KEY (Username, Path),
	FOREIGN KEY (Path) REFERENCES File (Path),
	FOREIGN KEY (Username) REFERENCES User (Username)
);

CREATE TABLE Playlist (
	Path			VARCHAR(500),
	Username		VARCHAR(20),
	PlayCount		INTEGER,
	Notes			VARCHAR(1000),
	WatchList		BOOLEAN,
	Favorite		BOOLEAN,
	Rating			INTEGER,
	CONSTRAINT pk_PlaylistID PRIMARY KEY (Username, Path),
	FOREIGN KEY (Path) REFERENCES File (Path),
	FOREIGN KEY (Username) REFERENCES User (Username)
);



-------------------------------------------------------------



CREATE TABLE Video (
	Path			VARCHAR(500),
	Title			VARCHAR(50) PRIMARY KEY,
	Year			INTEGER,
	RTID			INTEGER,
	IMDBID			INTEGER,
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE Poster (
	Title			VARCHAR(100),
	Year			INTEGER,
	Path			VARCHAR(500),
	PosterDetailed		VARCHAR(500),
	PosterOriginal		VARCHAR(500),
	PosterThumb		VARCHAR(500),
	PosterProfile		VARCHAR(500),
	CONSTRAINT pk_PosterID PRIMARY KEY (Title, Year, Path),
	FOREIGN KEY (Title) REFERENCES Video (Title),
	FOREIGN KEY (Year) REFERENCES Video (Year),
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE VideoFileDetails (
	Title			VARCHAR(100),
	Year			INTEGER,
	Path			VARCHAR(500),
	Duration		INTEGER,
	Width			INTEGER,
	Height			INTEGER,
	Codec			VARCHAR(20),
	AudioRate		VARCHAR(20),
	AudioEncoding		VARCHAR(20),
	FrameRate		VARCHAR(20),
	CONSTRAINT pk_VideoFileDetailsID PRIMARY KEY (Title, Year),
	FOREIGN KEY (Title) REFERENCES Video (Title),
	FOREIGN KEY (Year) REFERENCES Video (Year),
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE MovieDetails (
	Title			VARCHAR(100),
	Year			INTEGER,
	Path			VARCHAR(500),
	TheaterRelease	INTEGER,
	DVDRelease		INTEGER,
	RunTime			VARCHAR(15),
	Synopsis		VARCHAR(20),
	MPAARating		VARCHAR(20),
	CriticDescription	VARCHAR(20),
	CONSTRAINT pk_MovieDetailsID PRIMARY KEY (Title, Year),
	FOREIGN KEY (Title) REFERENCES Video (Title),
	FOREIGN KEY (Year) REFERENCES Video (Year),
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE Cast (
	Title			VARCHAR(100),
	Year			INTEGER,
	Path			VARCHAR(500),
	RTID			INTEGER PRIMARY KEY,
	Name			VARCHAR(35),
	FOREIGN KEY (Title) REFERENCES Video (Title),
	FOREIGN KEY (Year) REFERENCES Video (Year),
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE Character (
	RTID			INTEGER PRIMARY KEY,
	Name			VARCHAR(35),
	Path			VARCHAR(500)
	--missing foreign keys?
);

CREATE TABLE RTSearch (
	Title			VARCHAR(100),
	Year			INTEGER,
	Path			VARCHAR(500),
	Cast			VARCHAR(100),
	Reviews			VARCHAR(100),
	Similar			VARCHAR(100),
	Self			VARCHAR(100),
	Alternate		VARCHAR(100),
	Clips			VARCHAR(100),
	ResultNum		INTEGER,
	TotalResults		INTEGER,
	Query			VARCHAR(100),
	CONSTRAINT pk_RTSearchID PRIMARY KEY (Title, Year),
	FOREIGN KEY (Title) REFERENCES Video (Title),
	FOREIGN KEY (Year) REFERENCES Video (Year),
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE Image (
	Path			VARCHAR(500),
	Interlace		VARCHAR(100),
	Height			INTEGER,
	Width			INTEGER,
	Size			VARCHAR(20),
	ColorType		VARCHAR(20),
	BitDepth		VARCHAR(20),
	Gamma			VARCHAR(20),
	FOREIGN KEY (Path) REFERENCES File (Path)
);

CREATE TABLE Album (
	Title			VARCHAR(100),
	Artist			VARCHAR(100),
	Year			INTEGER,
	Genre			VARCHAR(20),
	Tracks			VARCHAR(500),
	Publisher		VARCHAR(20),
	ReleaseDate		VARCHAR(20),
	CONSTRAINT pk_AlbumID PRIMARY KEY (Title,Artist)
);

CREATE TABLE Audio (
	Title			VARCHAR(100),
	Band			VARCHAR(100),
	Year			INTEGER,
	Path			VARCHAR(500),
	AlbumTitle		VARCHAR(100),
	AlbumArtist		VARCHAR(100),
	AlbumYear		INTEGER,
	Artist			VARCHAR(100),
	Album			VARCHAR(100),
	Track			INTEGER,
	Genre			VARCHAR(20),
	Composer		VARCHAR(20),
	CONSTRAINT pk_AudioID PRIMARY KEY (Title,Band,Year),
	FOREIGN KEY (Path) REFERENCES File (Path),
	FOREIGN KEY (AlbumTitle) REFERENCES Album (Title),
	FOREIGN KEY (AlbumYear) REFERENCES Album (Year),
	FOREIGN KEY (AlbumArtist) REFERENCES Album (Artist)
);

CREATE TABLE AudioFileDetails (
	Title			VARCHAR(100),
	Band			VARCHAR(100),
	Year			INTEGER,
	Path			VARCHAR(500),
	Codec			VARCHAR(20),
	BitRate			VARCHAR(20),
	AudioLayer		VARCHAR(20),
	ChannelMode		VARCHAR(20),
	SampleRate		VARCHAR(20),
	Duration		VARCHAR(20),
	CONSTRAINT pk_AudioFileDetailsID PRIMARY KEY (Path, Title, Band, Year),
	FOREIGN KEY (Path) REFERENCES File (Path),
	FOREIGN KEY (Title) REFERENCES Audio (Title),
	FOREIGN KEY (Band) REFERENCES Audio (Band),
	FOREIGN KEY (Year) REFERENCES Audio (Year)
);

CREATE TABLE Hierarchy (
	Path			VARCHAR(500),
	SubDirPath		VARCHAR(500),
	ContainedFiles	INTEGER,
	LastSync		TIMESTAMP,
	CONSTRAINT pk_HierarchyID PRIMARY KEY (Path,SubDirPath)
);

CREATE TABLE Sync (
	DateTime		TIMESTAMP,
	Username		VARCHAR(20),
	TimerOrForced	BOOLEAN,
	LogPath			VARCHAR(500),
	FilesChecked		INTEGER,
	FilesSynced		INTEGER,
	CONSTRAINT pk_SyncID PRIMARY KEY (DateTime,Username),
	FOREIGN KEY (Username) REFERENCES User (Username)
);