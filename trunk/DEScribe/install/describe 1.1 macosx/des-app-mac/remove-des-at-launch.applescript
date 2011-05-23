tell application "System Events"
	--Find out what login items we have
	get the name of every login item
	--see if the item we want exists.  If so then delete it
	if login item "DEScribe" exists then
		delete login item "DEScribe"
	else
		display dialog "Cet item de démarrage au login n'existe pas ou plus."
	end if
end tell