Uses GraphABC;

function getTop(i:string):string;
begin
  result:=i.Insert(pos(ExtractFileExt(i),i)-1,'_top');
end;


function getBottom(i:string):string;
begin
  result:=i.Insert(pos(ExtractFileExt(i),i)-1,'_bottom');
end;


var S,B,T:Picture;
begin
 
foreach var i in EnumerateAllFiles(GetCurrentDir,'*.png') do
  begin
    S:=new Picture(i); if(S.Width=S.Height) then continue;
    B:=new Picture(16,16); B.CopyRect(Rect(0,0,16,16),S,Rect(0,16,32,32));
    T:=new Picture(16,16); T.CopyRect(Rect(0,0,16,16),S,Rect(0,0,16,16));
    B.Save(getBottom(i));
    T.Save(getTop(i));
  end;
CloseWindow();
end.