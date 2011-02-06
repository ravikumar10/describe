tell application "System Events"
	get exists login item "DEScribe"
	if result is false then
		make new login item at end of login items with properties {path:"/Applications/DEScribe/DEScribe.app", hidden:true, kind:application, name:"DEScribe"}
	end if
end tell
