; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{FC7EADD5-53D8-441C-AF1A-7810B43600F3}
AppName=DEScribe
AppVersion=1.1
AppVerName=DEScribe 1.1
AppPublisher=DEScribe Devs
AppPublisherURL=http://describe.googlecode.com/
AppSupportURL=http://describe.googlecode.com/
AppUpdatesURL=http://describe.googlecode.com/
DefaultDirName={pf}\DEScribe
DefaultGroupName=DEScribe
DisableProgramGroupPage=yes
LicenseFile=C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\COPYING.txt
OutputDir=C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\
OutputBaseFilename=DEScribe-1.1-setup
SetupIconFile=C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\des-icon.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "french"; MessagesFile: "compiler:Languages\French.isl"

[Files]
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\DEScribe.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\data"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\language\*"; DestDir: "{app}\language"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\media\*"; DestDir: "{app}\media"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\xml\*"; DestDir: "{app}\xml"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\README"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\COPYING"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Seb\UCBL\stage_describe\describe\DEScribe\install\describe 1.1 pc\sessions"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\DEScribe"; Filename: "{app}\DEScribe.exe"
Name: {commonstartup}\DEScribe; Filename: {app}\DEScribe.exe; WorkingDir: "{app}"
Name: "{group}\{cm:ProgramOnTheWeb,Describe}"; Filename: "http://describe.googlecode.com/"
Name: "{group}\{cm:UninstallProgram,Describe}"; Filename: "{uninstallexe}"

[Run]
Filename: "{app}\DEScribe.exe"; Description: "{cm:LaunchProgram,Describe}"; Flags: nowait postinstall skipifsilent

                                                                                      
