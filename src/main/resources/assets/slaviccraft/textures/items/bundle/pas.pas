{begin
   foreach var i in EnumerateAllFiles(GetCurrentDir,'*.png') do
     write(ExtractFileName(i).Replace('_bundle.png',''),' ');
end.
}

begin
  
  var A:=('artemisia cranesbill crysanth deeproot dried_herbs dried_kelp firewhip heather hellebore hemlock henbane immortelle lovage nettle primrose raveneye sleepgrass swiftfoot swordblade thistles thornapples wildrosemary').Split(' ');
  
  foreach var i in A do
  begin
      var S:=ReadAllText('hellebore_bundle.json');
      S:=S.Replace('items/bundle/hellebore','items/bundle/'+i);
     S:=S.Replace('hellebore_bundle',i+'_bundle');
      WriteAllText(i+'_bundle.json',S,Encoding.UTF8);
  end;
end.
