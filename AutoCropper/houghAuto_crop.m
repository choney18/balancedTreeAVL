function [x0, y0, x1, y1, x2, y2, x3, y3] = auto_crop ( f )
    bl = f(:,:,3);

    bl = medfilt2(bl);
   
    B2 = im2bw(bl,graythresh(bl));
    
    f = B2;
    
    [R,C] = size(f);
    f = imopen(f,ones(6,6));
    [L,~] = bwlabel(f,4);
    L = uint16(L);
    stats = regionprops(L,'Area');
    x = [stats.Area];
    for i=1:numel(x)
        if x(i) ~= max(x)
            L(L==i) = 0;
        end
    end
    se1 = strel('line',8,50);
    se2 = strel('line',8,125);
    
    L = imfill(L, 'holes');
    canny = edge(L,'Canny',[]);
    
    dil = imdilate(canny,[se2 se1]);
    
    [H,theta,ro] = hough(dil);
    maxHough = houghpeaks(H,4);
    lines = houghlines(dil,theta,ro,maxHough,'FillGap',125);    
    rho = abs([lines.rho]);
    
    index=find(rho==max(rho));
    minI=min(index);
    y2 = lines(minI).point2(2);
    x2 = lines(minI).point2(1);
    
    index=find(rho==min(rho));
    minI=min(index);
    y0 = lines(minI).point1(2);
    x0 = lines(minI).point1(1);     

    for k = 1:length(lines)
        
        v1(k,:) = [lines(k).point1];
        v2(k,:) = [lines(k).point2];
        
    end
    
    TR = [1,C];
    BL = [R,1];
    
    v = cat(1,v1,v2);
    
    if( length(v) == 8 )
        
            for k = 1:length(v) 
                
                TRdist(k)=pdist2(TR,v(k,:));
                BLdist(k)=pdist2(BL,v(k,:));
                
            end          
            
            index2=find(TRdist==min(TRdist));
            minp=min(index2);
            minsub=minp-4;
            
            if( minp >= 5 )
                
                y1=v2(minsub,2);
                x1=v2(minsub,1);
                
            elseif( minp < 5 ) 
                
                y1=v1(minp,2);
                x1=v1(minp,1);
                
            end   
            
            index1=find(BLdist==min(BLdist));
            minp=min(index1);
            minsub=minp-4;
            
            if( minp >= 5 )   
                
                y3=v2(minsub,2);
                x3=v2(minsub,1);
                
            elseif( minp < 5 )
                
                y3=v1(minp,2);
                x3=v1(minp,1);
                
            end
            
    else
        
        x3 = double(C*.25);
        y3 = double(R*.75);                        
        x1 = double(C*.75);
        y1 = double(R*.25);          
            
    end