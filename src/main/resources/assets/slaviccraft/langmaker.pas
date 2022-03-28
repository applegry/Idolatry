begin
  var lang :='en_us.json';
  Readln(lang);
  var F:Text;
  if(lang='en') then
  Rewrite(F,'en_us.json')
  else
  Rewrite(F,'ru_ru.json',Encoding.UTF8);
  var A:=ReadAllLines('D:\SlavicCraft\src\main\java\com\velesgod\slaviccraft\core\init\ItemInit.java');
  var B:array of string;
  SetLength(B,1);
  B[0]:='//#';
  writeln(F,'{');
  write(F,'$');
  foreach var S in A do begin
    if(Pos('"',S) > 0) then begin
      if(S.Contains('//@')) then continue;
      writeln(F,',');
      write(F,'"item.slaviccraft.');
      write(F,S.Split('"')[1],'":"');
      writeln(S);
      if(lang='en') then
      write(F,S.Split('#')[2].Replace('//',''),'"')
      else
      write(F,S.Split('#')[1].Replace('//',''),'"')
      end;
    end;
    writeln(F);
      writeln(F,'}');
    Close(F);
    if(lang='en') then
    WriteAllText('en_us.json',ReadAllText('en_us.json').Replace('$,',''),Encoding.UTF8)
    else
         WriteAllText('ru_ru.json',ReadAllText('ru_ru.json').Replace('$,',''),Encoding.UTF8)
end.