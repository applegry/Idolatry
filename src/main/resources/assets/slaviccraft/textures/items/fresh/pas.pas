begin

  var A:=('crysanth firewhip hellebore hemlock henbane immortelle kelp raveneye sleepgrass swiftfoot swordblade thornapples wildrosemary').Split(' ');

  foreach var i in A do
  begin
      var S:=ReadAllText('wildrosemary_bundle.json');
      S:=S.Replace('items/bundle/wildrosemary','items/fresh/'+i);
      S:=S.Replace('_bundle','_fresh');
      WriteAllText(i+'_fresh.json',S,Encoding.UTF8);
  end;
end.