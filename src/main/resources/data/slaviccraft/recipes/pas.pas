begin
  var A:=('swordblade swiftfoot firewhip immortelle crysanths wildrosemary raveneye thornapples sleepgrass henbane hemlock').Split(' ');

  foreach var i in A do
  begin
      var S:=ReadAllText('hellebore_fresh.json');
      S:=S.Replace('hellebore',i);
   //   S:=S.Replace('minecraft:hellebore_bundle','slaviccraft:'+i+'_fresh');
      WriteAllText(i+'_fresh.json',S,Encoding.UTF8);
  end;
end.