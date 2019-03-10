randomly generated midi files containing music 1m long
randomly generated images (500x500(for Instagram) or 1400x1400(if bandcamp))
randomly generated title
generated artist name (or random artists for each track)

STEP 0
	Choose programming language, make a plan
	
STEP 1
	Write a program generating midi files filled with random stuff 					-MIDIGEN
	Write a program generating image files using random seed (string)				-IMGGEN
	
STEP 2
	MIDIGEN: multiple instruments
	IMGGEN: save to PNG, blur, filters
	
STEP 3
	MIDIGEN: different type of generation for leads and drums
	IMGGEN: improve generator, make midifile binary as seed
	
STEP 4
	MIDIGEN: add some MUSIC THEORY, improve generator, generate from GUID
	IMGGEN: multi-iterational scheme
	
STEP 5 
	Make a GUI for MIDIGEN & IMGGEN
	Write a program generating title using random seed(GUID) 						-TITLEGEN
	//artist name/label name - first generated string
	Release alpha version
	
-----v0.1
	Freshly generated midis need to be listenable in-app
	Moderation - manual
	New Instagram account (username - generated artist name/label name if multiple artists)
	Video making - manual

STEP 6
	Add some neural network and evolution algorythms for IMGGEN
	MIDIGEN: continue improving
	
STEP 7 
	MIDIGEN: more MUSIC THEORY, genres
	TITLEGEN: midifile binaty as seed

STEP 8 
	Integrate EZWEBMER
	SIDEQUEST:
		Make EZWEBMER work smooth
	
STEP 9 
	Save files in any format:
		codecs for music(.mid .wav .mp3 .flac ...)
		codecs for images(.jpg .png ...)
		codecs for videos(.mp4 .webm ...) //from EZWEMBER

STEP 10
	Make MIDIGEN generate sound using samples

STEP 11 
	Make MIDIGEN use VSTs
	
...	
STEP X(or whatever)
	IMGGEN & MIDIGEN: Prettyfier, Cutyfier or whatever. Neural/evo for making midi and pics look,sound,feel nice and pretty
	Auto-iterations w/ auto-check 

-----v1.0
	Music generates automatically
	Moderation - automatically
	Video making - automatically
	Packages ready to export to music platforms(Bandcamp)