iTutor UI and Channel Readme

----------* * CARMetal UI * *----------

The UI is best learned through familiarizing yourself with the code itself.  This portion of the readme will include what changes have been made, and any 'tips and tricks' for navigating the CARMetal source code.


As of the end of 2016-2017 academic year, not much has been changed in the source code.  The first step taken was an attempt to refactor the source into a more compact form.  The desired package structure for the entire project was as thus:

	src/
		mainNonUI/
				*Main.java for the backend*
		mainUI/
				*Main.java for the frontend*
		ui/
				*frontend source files*
		backend/
				*backend source files*
	test/
		*test code*
		
The reason for this was that the UI is meant to be modular to the backend - in the event that CARMetal is deemed to no longer be the desired UI for the iTutor program, then it should be able to be replaced easily.

However, CARMetal's source code proved temperamental when this refactoring was attempted.  CARMetal's file structure (as of version 4.1.3) is as follows:

	src/
		atp/
			*fonts and special character translation*
		com/
			*as of the end of 2016-2017 academic year, unsure - seems to be mostly support for working on Apple machines*
		de/
			*2d vectors and other graphics support*
		eric/
			*
		icons/
			*as of version 4.1.3, this package is empty*
		net/
			*java color/palette support*
		netscape/
			*javascript support file*
		org/
			*additional javascript support*
		pm/
			*client/server support*
		rene/
			*contains the original CAR UI that CARMetal is built on*
		*Main.java is located in src, as well as several auxiliary files*
		
The initial goal when refactoring was to pull the main into a separate package as above, and place everything else into the ui package.  This caused a mass of compile issues, and the eventually the package structure in the current source code was settled upon.

	src/
		eric/
		rene/
		ui/
		mainUI/
		mainNonUI/
		backend/
		
Main.java and the auxiliary files are placed in mainUI, eric and rene are untouched, and ui contains an additional latex folder that does not come with the source, but is included in many files.  This latex folder is not required for the UI to compile, though the inclusion did remove red flags in the IDE.

***It is possible that the originally desired package structure could be created by moving the entirety of the CARMetal source into a ui folder, and having another Main.java in the mainUI/ package that calls the Main.java in the ui/ package***

When it comes to modifying the UI itself, the two packages that will be most useful to familiarize with will be the rene/ and eric/ packages.  The following is a list of all UI modifications that were made.

	eric/JSprograms/JSFunctions.java
		This file contained many function names written in French, and many of these names used special French characters such as e accent aigu or accent grave.  These characters were not properly 'translated' and thus used invalid combinations of characters.  The function names were not translated into English, though all invalid characters were modified so that compilation was possible.  Below is a list of all changes, ordered by what the intended accented character used to be.
		
		Accent Aigu
			EntrÃ©e -> Entree 
			DÃ©placer -> Deplacer 
			CrayonBaissÃ© -> CrayonBaisse 
			ExtrÃ©mitÃ© -> Extremite
			MettreIncrÃ©ment -> MettreIncrement
			MettreRayonMagnÃ©tique -> MettreRayonMagnetique
			MettreObjetsMagnÃ©tiques -> MettreObjetsMagnetiques
			AjouterObjetMagnÃ©tique -> AjouterObjetMagnetique
			LibÃ©rer -> Liberer
			MettreCachÃ© -> MettreCache
			SymÃ©trieAxiale -> SymetrieAxiale
			SymÃ©trieCentrale -> SymetrieCentrale
			SymÃ©trieCentrale3D -> SymetrieCentrale3D
			MÃ©diatrice -> Mediatrice
			IntersectionOrdonnÃ©e -> IntersectionOrdonnee
			ExÃ©cuterMacro -> ExecuterMacro
			FonctionCartÃ©sienne -> FonctionCartesienne
			FonctionParamÃ©trique -> FonctionParametrique
			TracÃ©Implicite -> TraceImplicite
			SymÃ©trie3DPlan -> Symetrie3DPlan
			
		Accent Grave
			QuadrilatÃ¨re -> Quadrilatere
			ParallÃ¨le -> Parallele
			SphÃ¨re -> SphEre //There is already a Sphere method
			SphÃ¨reRayon -> SphereRayon
	
	eric/monkey/monkey.java
		This file conducts the 'monkey button' that is located (roughly) in the upper right hand corner of the CARMetal UI.  Originally, pressing the monkey button would 'jumble' the drawn diagram, and for as long as the button was depressed the individual Objects within the diagram would move in unpredictable patterns.  The diagram returned to normal once the button was released.
		
		Instead of creating a new button somewhere in the UI, the monkey button was repurposed for the purpose of this project.  The original code has been commented out in case the originally functionality wants to be restored.  When pressed, the monkey button will now take the current canvas and frame from the UI, and pass it into a static function located in FromUI.java, in the channels package.  The canvas is where the drawn objects are located, while the frame is not currently used.
		
	rene/zirkel/*
		This package is where all of the UI objects, such as PointObject, LineObject, CircleObject, etc., and their constructors are located.  You will primarily work in the objects package located here.  This is where the objects themselves are defined.  These are what the canvas stores information about, and what must be passed back to the backend.
		
Channels
	The channels package currently contains files and functions meant to handle the passing of a diagram from the UI to the backend.  FromUI is where the canvas is parsed through, and all objects handled.  When an object is identified, it should be sent for translation to an appropriate function in FromUITranslation.java.  For some objects, it is possible to utilize pre-existing functions instead of writing a new function entirely.  The UI objects are all built utilizing inheritance, and so it is important to find the most specific instanceof class possible - the hierarchy is detailed in UI_Inheritance.txt, and a skeleton to handle it is in place.
	
	Currently, the UI->Backend channel can handle points, midpoints(as a regular point), segments, vectors(that are simply passed back as segments).  Lines and parallel lines have been implemented partially, but not tested.  
	
TODO:  These include both work that is for immediate attention, and eventual goals.  The further down the list, the less immediate the attention.
	-Finish implementation of Line and ParallelLine translation.  The code is mostly implemented, there is a special case that is defined within the code.  The same solution should fit for both.  
	
	-Test that Lines and Parallel Lines are handled in a way that is desired and beneficial.
	
	-Begin implementation of translation of other important objects.  
	--JPN: The desire to pass back arcs was an idea that has been expressed.  Having worked with how arcs are handled in the precomputer and fact computer, I do not think this is required - as long as the points are correctly interpreted as being on the circle, then the arc should be created in the backend.
	
	-Implementation of Backend->UI channel.  
	--JPN: The first thing to do would be to implement translation for backend objects to UI objects.  This should be a much simpler task than the other direction, as the backend does not have a concept of unique objects for unique concepts, such as an individual line, ray, or parallel line.  However, there are issues such as the frontend not having a well defined concept of an arc.  Once translation is done, all objects that are not in the canvas should be added.  It might be simpler to grab the current canvas, convert it to a diagram, and using backend functions add in all new objects (this would naturally be more runtime intensive than simply implementing a check for the UI objects).  Once all objects are added, calling "*canvasname*.repaint();" should redraw the canvas for the user.
	
	-Implementation of shading
	--JPN: This is technically part of the previous TODO.  The UI does have the concept of a shaded area, thought it is a separate object.  To shade an are from the backend, you will have to create a shaded area object with the correct dimensions and coordinates.  For a circle this might be tricky - Dr. Alvin would be the best person to confer with *once this step is reached*.
















