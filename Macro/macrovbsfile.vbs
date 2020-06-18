Option Explicit

Dim xlApp
Dim xlBook
Dim fso
Set fso = CreateObject("Scripting.FileSystemObject")
Dim fullpath
fullpath = fso.GetAbsolutePathName(".")
Set fso = Nothing

Set xlApp = CreateObject("Excel.Application")
'~~> Change Path here
Set xlBook = xlApp.Workbooks.Open("D:\Automation\ProcessName\macro\macro1.xlsm", 0, True)
xlApp.Run "getconnection"
xlApp.DisplayAlerts = False
xlBook.Close
xlApp.DisplayAlerts = True
xlApp.Quit

Set xlBook = Nothing
Set xlApp = Nothing

'WScript.Echo "Finished."
WScript.Quit