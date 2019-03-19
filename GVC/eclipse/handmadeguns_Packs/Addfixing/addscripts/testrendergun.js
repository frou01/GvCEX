//function renderFirst(renderinstance,gunitem,gunstack,model,entity,type,data){
////var minecraft = renderinstance.getminecraft();//マインクラフトのインスタンス。多分使わない
////model.renderAll();
////org.lwjgl.opengl.GL11.glRotatef(180, 1.0, 0.0, 0.0);
////org.lwjgl.opengl.GL11.glRotatef(50, 0.0, 1.0, 0.0);
////org.lwjgl.opengl.GL11.glRotatef(180, 0.0, 0.0, 1.0);
////var cocking = renderinstance.getbooleanfromnbt("Cocking");//コッキングしているかどうか
////var cockingprogress = renderinstance.getintfromnbt("CockingTime") + renderinstance.getSmoothing() - 1;//コッキング開始からの時間（float値）
//var recoiled = renderinstance.getbooleanfromnbt("Recoiled");//リコイル中かどうか（銃口が一瞬跳ねる様に描画するためのフラグ）
////var recoiledtime = renderinstance.getintfromnbt("RecoiledTime");//使途不明
////var boltprogress = renderinstance.getbytefromnbt("Bolt");//ボルト、コッキングしないAR・セミSR系で給弾のアニメーションを書くため。byte値(int様に扱えるはず)
//var isreloading = renderinstance.getbooleanfromnbt("IsReloading")//リロード中かどうか
////var Crotex = renderinstance.getfloatfromnbt("rotex");//シリンダーの回転X
////var Crotey = renderinstance.getfloatfromnbt("rotey");//シリンダーの回転Y
////var Crotez = renderinstance.getfloatfromnbt("rotez");//シリンダーの回転Z
////boltprogress -= renderinstance.getSmoothing();
////var cycle = gunitem.cycle;//銃の給弾にかかる時間（for AR/semiSR）
////var boltoffsetcof;//槓桿の往復を再現するための物。boltoffsetcofは0-1を動きます。これで一瞬スライドが戻る様子を描画出来ます。
////if (boltprogress < cycle / 2)//ボルト後退中
////	boltoffsetcof = cycle - boltprogress;
////else
////	boltoffsetcof = cycle - (cycle - boltprogress);
////if (boltoffsetcof < 0) boltoffsetcof = 0;
//org.lwjgl.opengl.GL11.glPushMatrix();//開始時にこれを呼んで行列をバックアップします。
////renderinstance.bindGuntexture()//銃のテクスチャを使用するためのメソッド。スクリプト描画に入る前に割り当てているので通常は使うことは無いかと思われます。
////glTranslatefで移動、描画→パーツが移動されて描画される。
////glTranslatefで移動、glRotatefで回転→移動先で回転されて描画される。
////glTranslatefで移動、glRotatefで回転、glTranslatefで最初と同じ量逆向きに移動→ある点を中心に回転したことになる。
//if (Packages.handmadeguns.mod_HandmadeGuns.Key_ADS(entity)){
//    //ADS中に呼ばれる部分
//	if (isreloading){
//	    //ADSかつリロード中に呼ばれる部分
//	    var reloadprogress = renderinstance.getintfromnbt("RloadTime");
//		var sighttype = renderinstance.bindAttach_SightPosition_and_getSightType(entity,gunstack);//サイトを覗くために位置を調整し、同時に種類を取得
//		renderinstance.glMatrixForRenderInEquipped_reload();//銃に設定されている描画位置を適用
//        renderpartsOnReload(renderinstance,gunitem,gunstack,model,entity,type,data);
//	} else {
//	    if (!recoiled) {
//        	renderinstance.glMatrixForRenderInEquippedADS(-1.4 - 0.03 * (1 - renderinstance.getSmoothing()));
//        } else {
//        	renderinstance.glMatrixForRenderInEquippedADS(-1.4);
//        }
//        renderpartsNormal(renderinstance,gunitem,gunstack,model,entity,type,data);
//	}
//
//} else {
//	if (isreloading) {
//	    //リロード中に呼ばれます。
//	    //リロード開始からの時間はRloadTimeで取れます。
//		renderinstance.glMatrixForRenderInEquipped_reload();//描画始点をリロード中位置へ。glTranslatefで移動しても問題ない。
//		renderpartsOnReload(renderinstance,gunitem,gunstack,model,entity,type,data);
//	} else {
//		var sighttype = renderinstance.bindAttach_SightPosition_and_getSightType(entity,gunstack);//サイトを覗くために位置を調整し、同時に種類を取得
//	    if (renderinstance.isentitysprinting(entity) && !renderinstance.getbooleanfromnbt("isBursting")) {
//    		renderinstance.glMatrixForRenderInEquipped(0);
//    		org.lwjgl.opengl.GL11.glRotatef(gunitem.Sprintrotationx, 1.0, 0.0, 0.0);
//    		org.lwjgl.opengl.GL11.glRotatef(gunitem.Sprintrotationy, 0.0, 1.0, 0.0);
//    		org.lwjgl.opengl.GL11.glRotatef(gunitem.Sprintrotationz, 0.0, 0.0, 1.0);
//    		org.lwjgl.opengl.GL11.glTranslatef(gunitem.Sprintoffsetx, gunitem.Sprintoffsety, gunitem.Sprintoffsetz);
//    	}else{
//		    if (!recoiled) {
//		        //射撃後の1tick中のみ呼ばれます
//		    	renderinstance.glMatrixForRenderInEquipped(-0.2 - 0.005 * (1 - renderinstance.getSmoothing()));
//		    	org.lwjgl.opengl.GL11.glRotatef(gunitem.jump * (1 - renderinstance.getSmoothing()), 1.0, 0.0, 0.0);//銃口跳ね上がり。この状態だとモデルの0,0,0を中心に回転。
//
//
//		    } else {
//		        //通常状態
//		    	renderinstance.glMatrixForRenderInEquipped(-0.2);
//		    }
//		}
//        renderpartsNormal(renderinstance,gunitem,gunstack,model,entity,type,data);
//	}
//}
//org.lwjgl.opengl.GL11.glPopMatrix();//終了時にこれを呼んで行列をバックアップから戻します。
//}
//function renderunder(renderinstance,gunitem,gunstack,model,entity,type,data){
//    if(!gunitem.useundergunsmodel){
//        var underType = renderinstance.getUnderbarrelAttachType(entity,gunstack);//銃身下のアタッチメントの種類を取得
//        if(underType == 0) {
//            model.renderPart("mat9");
//        } else if(underType == 1){
//            model.renderPart("mat13");
//        } else if(underType == 5){
//            model.renderPart("mat10");
//        } else if(underType == 6){
//            model.renderPart("mat11");
//        } else {
//            model.renderPart("mat21");
//        }
//    }else{
//        var underType = renderinstance.getUnderbarrelAttachType(entity,gunstack);//銃身下のアタッチメントの種類を取得
//        if(underType == 0) {
//            model.renderPart("mat9");
//        } else if(underType == 1){
//            model.renderPart("mat13");
//        } else if(underType == 5) {
//            model.renderPart("mat10");
//        } else if(underType == 6) {
//            model.renderPart("mat11");
//        } else {
//            model.renderPart("mat21");
//        }
//        var underStack = renderinstance.getUnderbarrelAttachStack(entity,gunstack);
//        renderinstance.underRend_useunderGunModel(gunitem,underStack,type,data);
//    }
//}
//function rendesight(renderinstance,gunitem,gunstack,model,entity,type,data){
//    var sighttype = renderinstance.getAttach_SightType(entity,gunstack);
//    if(sighttype == 0){
//        model.renderPart("mat20");
//    }else if(sighttype == 1){
//        renderinstance.setLighting(240,240);
//        model.renderPart("mat4");
//        renderinstance.setLighting(renderinstance.getFirstpersonLighting()[0],renderinstance.getFirstpersonLighting()[1])
//    }else if(sighttype == 2){
//        model.renderPart("mat5");
//    }
//}
//function renderpartsOnReload(renderinstance,gunitem,gunstack,model,entity,type,data){
//    var cocking = renderinstance.getbooleanfromnbt("Cocking");//コッキングしているかどうか
//    var cockingprogress = renderinstance.getintfromnbt("CockingTime") + renderinstance.getSmoothing() - 1;//コッキング開始からの時間（float値）
//    var recoiled = renderinstance.getbooleanfromnbt("Recoiled");//リコイル中かどうか（銃口が一瞬跳ねる様に描画するためのフラグ）
//    //var recoiledtime = renderinstance.getintfromnbt("RecoiledTime");//使途不明
//    var boltprogress = renderinstance.getbytefromnbt("Bolt");//ボルト、コッキングしないAR・セミSR系で給弾のアニメーションを書くため。byte値(int様に扱えるはず)
//    var isreloading = renderinstance.getbooleanfromnbt("IsReloading")//リロード中かどうか
//    var Crotex = renderinstance.getfloatfromnbt("rotex");//シリンダーの回転X
//    var Crotey = renderinstance.getfloatfromnbt("rotey");//シリンダーの回転Y
//    var Crotez = renderinstance.getfloatfromnbt("rotez");//シリンダーの回転Z
//    boltprogress -= renderinstance.getSmoothing();
//    var cycle = gunitem.cycle;//銃の給弾にかかる時間（for AR/semiSR）
//    var boltoffsetcof;//槓桿の往復を再現するための物。boltoffsetcofは0-1を動きます。これで一瞬スライドが戻る様子を描画出来ます。
//    if (boltprogress < cycle / 2)//ボルト後退中
//    	boltoffsetcof = cycle - boltprogress;
//    else
//    	boltoffsetcof = cycle - (cycle - boltprogress);
//    if (boltoffsetcof < 0) boltoffsetcof = 0;
//    var reloadprogress = renderinstance.getintfromnbt("RloadTime");
//    var sighttype = renderinstance.getAttach_SightType(entity,gunstack);//サイト種類を取得
//    org.lwjgl.opengl.GL11.glScalef(renderinstance.modelscala,renderinstance.modelscala,renderinstance.modelscala);
//
//    model.renderPart("mat1");
//    //マガジンを下から差し込む
//    org.lwjgl.opengl.GL11.glTranslatef(0,-(gunitem.reloadtime - reloadprogress)*0.1,0);
//    model.renderPart("mat3");
//    org.lwjgl.opengl.GL11.glTranslatef(0,(gunitem.reloadtime - reloadprogress)*0.1,0);
//
//    org.lwjgl.opengl.GL11.glTranslatef(0.0, 0.0, -renderinstance.mat2offsetz);
//    model.renderPart("mat2");//スライド
//    org.lwjgl.opengl.GL11.glTranslatef(0.0, 0.0, renderinstance.mat2offsetz);
//
//    org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat25offsetx, gunitem.mat25offsety, gunitem.mat25offsetz);
//    org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationx, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationy, 0.0, 1.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationz, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat25offsetx, -gunitem.mat25offsety, -gunitem.mat25offsetz);
//    org.lwjgl.opengl.GL11.glTranslatef(0, 0, gunitem.cocktime * 0.1);
//    model.renderPart("mat25");
//    org.lwjgl.opengl.GL11.glTranslatef(0, 0, -gunitem.cocktime * 0.1);
//    org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat25offsetx, gunitem.mat25offsety, gunitem.mat25offsetz);
//    org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationx, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationy, 0.0, 1.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationz, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat25offsetx, -gunitem.mat25offsety, -gunitem.mat25offsetz);
//
//    org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//    org.lwjgl.opengl.GL11.glRotatef(Crotey, 0.0, 1.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(Crotex, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(Crotez, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//    model.renderPart("mat31");//ハンマー
//    org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//    org.lwjgl.opengl.GL11.glRotatef(-Crotey, 0.0, 1.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(-Crotex, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(-Crotez, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//
//    //弾帯カバー等
//    org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat22offsetx, gunitem.mat22offsety, gunitem.mat22offsetz);
//    org.lwjgl.opengl.GL11.glRotatef(gunitem.mat22rotationx, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(gunitem.mat22rotationy, 0.0, 1.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(gunitem.mat22rotationz, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat22offsetx, -gunitem.mat22offsety, -gunitem.mat22offsetz);
//    model.renderPart("mat22");
//    org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat22offsetx, gunitem.mat22offsety, gunitem.mat22offsetz);
//    org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat22rotationx, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat22rotationy, 0.0, 1.0, 0.0);
//    org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat22rotationz, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat22offsetx, -gunitem.mat22offsety, -gunitem.mat22offsetz);
//
//    org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat32posx, renderinstance.mat32posy, renderinstance.mat32posz);//0,0.5,0
//    org.lwjgl.opengl.GL11.glRotatef(renderinstance.mat32rotey, 0.0, 1.0, 0.0);//90
//    org.lwjgl.opengl.GL11.glRotatef(renderinstance.mat32rotez, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glRotatef(renderinstance.mat32rotex, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat32posx, -renderinstance.mat32posy, -renderinstance.mat32posz);
//    model.renderPart("mat32");
//    org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat32posx, renderinstance.mat32posy, renderinstance.mat32posz);//0,0.5,0
//    org.lwjgl.opengl.GL11.glRotatef(-renderinstance.mat32rotey, 0.0, 1.0, 0.0);//90
//    org.lwjgl.opengl.GL11.glRotatef(-renderinstance.mat32rotez, 0.0, 0.0, 1.0);
//    org.lwjgl.opengl.GL11.glRotatef(-renderinstance.mat32rotex, 1.0, 0.0, 0.0);
//    org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat32posx, -renderinstance.mat32posy, -renderinstance.mat32posz);
//    rendesight(renderinstance,gunitem,gunstack,model,entity,type,data);
//    renderunder(renderinstance,gunitem,gunstack,model,entity,type,data);
//
//    org.lwjgl.opengl.GL11.glRotatef(180, 1.0, 0.0, 0.0);//マイクラの腕描画と座標系がズレているらしいので修正用
//    org.lwjgl.opengl.GL11.glTranslatef(0, -0.5, 0);
//    renderinstance.bindPlayertexture(entity);//プレイヤーのテクスチャを使用するためのメソッド
//    renderinstance.renderarm(renderinstance.armrotationxl,
//                             renderinstance.armrotationyl,
//                             renderinstance.armrotationzl,
//                             renderinstance.armoffsetxl,
//                             renderinstance.armoffsetyl + (gunitem.reloadtime - reloadprogress)*0.1+1,//腕を上に向かって動かす。
//                             renderinstance.armoffsetzl,//ここまで左手
//                             renderinstance.armrotationxr,
//                             renderinstance.armrotationyr,
//                             renderinstance.armrotationzr,
//                             renderinstance.armoffsetxr,
//                             renderinstance.armoffsetyr,
//                             renderinstance.armoffsetzr);//腕を描画するためのメソッド。直に使うことも不可能では無いですが難読化がかかっているため面倒なことになります。
//                             //位置その他はとりあえず設定値をそのまま利用
//                             //回転量ひょっとしてラヂアン…？
//}
//function renderpartsNormal(renderinstance,gunitem,gunstack,model,entity,type,data){
//    var cocking = renderinstance.getbooleanfromnbt("Cocking");//コッキングしているかどうか
//    var cockingprogress = renderinstance.getintfromnbt("CockingTime") + renderinstance.getSmoothing() - 1;//コッキング開始からの時間（float値）
//    var recoiled = renderinstance.getbooleanfromnbt("Recoiled");//リコイル中かどうか（銃口が一瞬跳ねる様に描画するためのフラグ）
//    //var recoiledtime = renderinstance.getintfromnbt("RecoiledTime");//使途不明
//    var boltprogress = renderinstance.getbytefromnbt("Bolt");//ボルト、コッキングしないAR・セミSR系で給弾のアニメーションを書くため。byte値(int様に扱えるはず)
//    var isreloading = renderinstance.getbooleanfromnbt("IsReloading")//リロード中かどうか
//    var Crotex = renderinstance.getfloatfromnbt("rotex");//シリンダーの回転X
//    var Crotey = renderinstance.getfloatfromnbt("rotey");//シリンダーの回転Y
//    var Crotez = renderinstance.getfloatfromnbt("rotez");//シリンダーの回転Z
//    boltprogress -= renderinstance.getSmoothing();
//    var cycle = gunitem.cycle;//銃の給弾にかかる時間（for AR/semiSR）
//    var boltoffsetcof;//槓桿の往復を再現するための物。boltoffsetcofは0-1を動きます。これで一瞬スライドが戻る様子を描画出来ます。
//    if (boltprogress < cycle / 2)//ボルト後退中
//    	boltoffsetcof = cycle - boltprogress;
//    else
//    	boltoffsetcof = cycle - (cycle - boltprogress);
//    if (boltoffsetcof < 0) boltoffsetcof = 0;
//    org.lwjgl.opengl.GL11.glScalef(renderinstance.modelscala,renderinstance.modelscala,renderinstance.modelscala);
//    model.renderPart("mat1");
//    model.renderPart("mat3");
//    if (!recoiled){
//        org.lwjgl.opengl.GL11.glTranslatef(0.0, 0.0, -renderinstance.mat2offsetz * boltoffsetcof * (1 - renderinstance.getSmoothing()));
//        model.renderPart("mat2");//スライド
//        //boltoffsetcofは1tick中に0-1間を一往復するので一瞬後退する様子を描画できる。
//
//        org.lwjgl.opengl.GL11.glTranslatef(0.0, 0.0, renderinstance.mat2offsetz * boltoffsetcof * (1 - renderinstance.getSmoothing()));
//
//
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(Crotey - renderinstance.mat31rotey * (1-renderinstance.getSmoothing()), 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotex - renderinstance.mat31rotex * (1-renderinstance.getSmoothing()), 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotez - renderinstance.mat31rotez * (1-renderinstance.getSmoothing()), 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//        model.renderPart("mat31");//シリンダー
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(-(Crotey - renderinstance.mat31rotey * (1-renderinstance.getSmoothing())), 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-(Crotex - renderinstance.mat31rotex * (1-renderinstance.getSmoothing())), 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-(Crotez - renderinstance.mat31rotez * (1-renderinstance.getSmoothing())), 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat32posx, renderinstance.mat32posy, renderinstance.mat32posz);//0,0.5,0
//        org.lwjgl.opengl.GL11.glRotatef(renderinstance.mat32rotey*Math.abs(0.5-renderinstance.getSmoothing())*2, 0.0, 1.0, 0.0);//90
//        org.lwjgl.opengl.GL11.glRotatef(renderinstance.mat32rotez*Math.abs(0.5-renderinstance.getSmoothing())*2, 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glRotatef(renderinstance.mat32rotex*Math.abs(0.5-renderinstance.getSmoothing())*2, 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat32posx, -renderinstance.mat32posy, -renderinstance.mat32posz);
//        model.renderPart("mat32");//ハンマー
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat32posx, renderinstance.mat32posy, renderinstance.mat32posz);//0,0.5,0
//        org.lwjgl.opengl.GL11.glRotatef(-renderinstance.mat32rotey*Math.abs(0.5-renderinstance.getSmoothing())*2, 0.0, 1.0, 0.0);//90
//        org.lwjgl.opengl.GL11.glRotatef(-renderinstance.mat32rotez*Math.abs(0.5-renderinstance.getSmoothing())*2, 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glRotatef(-renderinstance.mat32rotex*Math.abs(0.5-renderinstance.getSmoothing())*2, 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat32posx, -renderinstance.mat32posy, -renderinstance.mat32posz);
//    }else{
//        model.renderPart("mat2");//スライド
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(Crotey, 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotex, 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotez, 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//        model.renderPart("mat31");//シリンダー
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(-Crotey, 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-Crotex, 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-Crotez, 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//        model.renderPart("mat32");//ハンマー
//    }
//    if (cockingprogress <= 0) {
//    	model.renderPart("mat25");
//    } else {
//    	org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat25offsetx, gunitem.mat25offsety, gunitem.mat25offsetz);
//    	org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationx, 1.0, 0.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationy, 0.0, 1.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationz, 0.0, 0.0, 1.0);
//    	org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat25offsetx, -gunitem.mat25offsety, -gunitem.mat25offsetz);
//    	if (cockingprogress > 0 && cockingprogress < ((gunitem.cocktime + renderinstance.getSmoothing() - 1) / 2)) {
//        	org.lwjgl.opengl.GL11.glTranslatef(0, 0, -(cockingprogress+renderinstance.getSmoothing()) * 0.1);
//        } else {
//        	org.lwjgl.opengl.GL11.glTranslatef(0, 0, (cockingprogress + renderinstance.getSmoothing() - gunitem.cocktime) * 0.1);
//        }
//        model.renderPart("mat25");//槓桿
//        if (cockingprogress > 0 && cockingprogress < ((gunitem.cocktime + renderinstance.getSmoothing() - 1) / 2)) {
//        	org.lwjgl.opengl.GL11.glTranslatef(0, 0, (cockingprogress+renderinstance.getSmoothing()) * 0.1);
//        } else {
//        	org.lwjgl.opengl.GL11.glTranslatef(0, 0, -(cockingprogress + renderinstance.getSmoothing() - gunitem.cocktime) * 0.1);
//        }
//    	org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat25offsetx, gunitem.mat25offsety, gunitem.mat25offsetz);
//    	org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationx, 1.0, 0.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationy, 0.0, 1.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationz, 0.0, 0.0, 1.0);
//    	org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat25offsetx, -gunitem.mat25offsety, -gunitem.mat25offsetz);
//    }
//    //弾帯カバー等
//    model.renderPart("mat22");
//    rendesight(renderinstance,gunitem,gunstack,model,entity,type,data);
//
//    renderunder(renderinstance,gunitem,gunstack,model,entity,type,data);
//
//
//    org.lwjgl.opengl.GL11.glRotatef(180, 1.0, 0.0, 0.0);//マイクラの腕描画と座標系がズレているらしいので修正用
//    org.lwjgl.opengl.GL11.glTranslatef(0, -0.5, 0);
//    renderinstance.bindPlayertexture(entity);//プレイヤーのテクスチャを使用するためのメソッド
//    renderinstance.renderarm(renderinstance.armrotationxl,
//                             renderinstance.armrotationyl,
//                             renderinstance.armrotationzl,
//                             renderinstance.armoffsetxl,
//                             renderinstance.armoffsetyl,
//                             renderinstance.armoffsetzl,
//                             renderinstance.armrotationxr,
//                             renderinstance.armrotationyr,
//                             renderinstance.armrotationzr,
//                             renderinstance.armoffsetxr,
//                             renderinstance.armoffsetyr,
//                             renderinstance.armoffsetzr);//腕を描画するためのメソッド。直に使うことも不可能では無いですが難読化がかかっているため面倒なことになります。
//                             //位置その他はとりあえず設定値をそのまま利用
//}
//function rendesight_third(renderinstance,gunitem,gunstack,model,entity,type,data){
//    var sighttype = renderinstance.getAttach_SightType(entity,gunstack);
//    if(sighttype == 0){
//        model.renderPart("mat20");
//    }else if(sighttype == 1){
//        renderinstance.setLighting(240,240);
//        model.renderPart("mat4");
//        renderinstance.setLighting(renderinstance.getThirdpersonLighting(entity)[0],renderinstance.getThirdpersonLighting(entity)[1])
//    }else if(sighttype == 2){
//        model.renderPart("mat5");
//    }
//}
//function renderThird(renderinstance,gunitem,gunstack,model,entity,type,data){
//    var cocking = renderinstance.getbooleanfromnbt("Cocking");//コッキングしているかどうか
//    var cockingprogress = renderinstance.getintfromnbt("CockingTime") + renderinstance.getSmoothing() - 1;//コッキング開始からの時間（float値）
//    var recoiled = renderinstance.getbooleanfromnbt("Recoiled");//リコイル中かどうか（銃口が一瞬跳ねる様に描画するためのフラグ）
////    var recoiledtime = renderinstance.getintfromnbt("RecoiledTime");//使途不明
//    var boltprogress = renderinstance.getbytefromnbt("Bolt");//ボルト、コッキングしないAR・セミSR系で給弾のアニメーションを書くため。byte値(int様に扱えるはず)
//    var isreloading = renderinstance.getbooleanfromnbt("IsReloading")//リロード中かどうか
//    var Crotex = renderinstance.getfloatfromnbt("rotex");//シリンダーの回転X
//    var Crotey = renderinstance.getfloatfromnbt("rotey");//シリンダーの回転Y
//    var Crotez = renderinstance.getfloatfromnbt("rotez");//シリンダーの回転Z
//    boltprogress -= renderinstance.getSmoothing();
//    var cycle = gunitem.cycle;//銃の給弾にかかる時間（for AR/semiSR）
//    var boltoffsetcof;//槓桿の往復を再現するための物。boltoffsetcofは0-1を動きます。これで一瞬スライドが戻る様子を描画出来ます。
//    if (boltprogress < cycle / 2)//ボルト後退中
//    	boltoffsetcof = cycle - boltprogress;
//    else
//    	boltoffsetcof = cycle - (cycle - boltprogress);
//    if (boltoffsetcof < 0) boltoffsetcof = 0;
//    org.lwjgl.opengl.GL11.glPushMatrix();
//    var minecraft = renderinstance.getminecraft();
//    var sighttype = renderinstance.getAttach_SightType(entity,gunstack);
//    if(Packages.handmadeguns.mod_HandmadeGuns.islmmloaded && entity instanceof Packages.littleMaidMobX.LMM_EntityLittleMaid){
//        if (Packages.handmadeguns.mod_HandmadeGuns.cfg_RenderGunSizeLMM) {
//        	renderinstance.glMatrixForRenderInEntityLMM(0);
//            org.lwjgl.opengl.GL11.glScalef(renderinstance.modelscala - 0.25, renderinstance.modelscala - 0.25, renderinstance.modelscala - 0.25);
//            org.lwjgl.opengl.GL11.glTranslatef(0.5, 1.0, -0.3);
//        } else {
//        	renderinstance.glMatrixForRenderInEntity(0);
//        	org.lwjgl.opengl.GL11.glScalef(renderinstance.modelscala - 0.2, renderinstance.modelscala - 0.2, renderinstance.modelscala - 0.2);
//        }
//    } else if(renderinstance.is_entity_player(entity)){
//        renderinstance.glMatrixForRenderInEntityPlayer(0);
//        org.lwjgl.opengl.GL11.glTranslatef(0.0, 0.0, -0.3);
//        org.lwjgl.opengl.GL11.glScalef(renderinstance.modelscala, renderinstance.modelscala, renderinstance.modelscala);
//    }else{
//    	renderinstance.glMatrixForRenderInEntity(0);
//    	org.lwjgl.opengl.GL11.glScalef(renderinstance.modelscala - 0.2, renderinstance.modelscala - 0.2, renderinstance.modelscala - 0.2);
//    }
//    model.renderPart("mat1");
//    rendesight_third(renderinstance,gunitem,gunstack,model,entity,type,data);
//    renderunder(renderinstance,gunitem,gunstack,model,entity,type,data);
//    if(isreloading){
//    }else{
//        model.renderPart("mat3");
//    }
//    if (!recoiled){
//        org.lwjgl.opengl.GL11.glTranslatef(0.0, 0.0, -renderinstance.mat2offsetz * boltoffsetcof * (1 - renderinstance.getSmoothing()));
//        model.renderPart("mat2");//スライド
//        //boltoffsetcofは1tick中に0-1間を一往復するので一瞬後退する様子を描画できる。
//        //glTranslatefで移動、描画→パーツが移動されて描画される。
//        //glTranslatefで移動、glRotatefで回転→移動先で回転されて描画される。
//        //glTranslatefで移動、glRotatefで回転、glTranslatefで最初と同じ量逆向きに移動→ある点を中心に回転したことになる。
//        org.lwjgl.opengl.GL11.glTranslatef(0.0, 0.0, renderinstance.mat2offsetz * boltoffsetcof * (1 - renderinstance.getSmoothing()));
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(Crotey - renderinstance.mat31rotey * (1-renderinstance.getSmoothing()), 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotex - renderinstance.mat31rotex * (1-renderinstance.getSmoothing()), 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotez - renderinstance.mat31rotez * (1-renderinstance.getSmoothing()), 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//        model.renderPart("mat31");
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(-(Crotey - renderinstance.mat31rotey * (1-renderinstance.getSmoothing())), 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-(Crotex - renderinstance.mat31rotex * (1-renderinstance.getSmoothing())), 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-(Crotez - renderinstance.mat31rotez * (1-renderinstance.getSmoothing())), 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//    }else{
//        model.renderPart("mat2");
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(Crotey, 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotex, 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(Crotez, 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//        model.renderPart("mat31");
//        org.lwjgl.opengl.GL11.glTranslatef(renderinstance.mat31posx, renderinstance.mat31posy, renderinstance.mat31posz);//0,0.7,0
//        org.lwjgl.opengl.GL11.glRotatef(-Crotey, 0.0, 1.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-Crotex, 1.0, 0.0, 0.0);
//        org.lwjgl.opengl.GL11.glRotatef(-Crotez, 0.0, 0.0, 1.0);
//        org.lwjgl.opengl.GL11.glTranslatef(-renderinstance.mat31posx, -renderinstance.mat31posy, -renderinstance.mat31posz);
//    }
//    if (cockingprogress <= 0) {
//    	model.renderPart("mat25");
//    } else {
//    	org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat25offsetx, gunitem.mat25offsety, gunitem.mat25offsetz);
//    	org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationx, 1.0, 0.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationy, 0.0, 1.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(gunitem.mat25rotationz, 0.0, 0.0, 1.0);
//    	org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat25offsetx, -gunitem.mat25offsety, -gunitem.mat25offsetz);
//    	if (cockingprogress > 0 && cockingprogress < ((gunitem.cocktime + renderinstance.getSmoothing() - 1) / 2)) {
//    		org.lwjgl.opengl.GL11.glTranslatef(0, 0, -(cockingprogress+renderinstance.getSmoothing()) * 0.1);
//    	} else {
//    		org.lwjgl.opengl.GL11.glTranslatef(0, 0, (cockingprogress + renderinstance.getSmoothing() - gunitem.cocktime) * 0.1);
//    	}
//    	model.renderPart("mat25");
//    	if (cockingprogress > 0 && cockingprogress < ((gunitem.cocktime + renderinstance.getSmoothing() - 1) / 2)) {
//        	org.lwjgl.opengl.GL11.glTranslatef(0, 0, (cockingprogress+renderinstance.getSmoothing()) * 0.1);
//        } else {
//        	org.lwjgl.opengl.GL11.glTranslatef(0, 0, -(cockingprogress + renderinstance.getSmoothing() - gunitem.cocktime) * 0.1);
//        }
//    	org.lwjgl.opengl.GL11.glTranslatef(gunitem.mat25offsetx, gunitem.mat25offsety, gunitem.mat25offsetz);
//    	org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationx, 1.0, 0.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationy, 0.0, 1.0, 0.0);
//    	org.lwjgl.opengl.GL11.glRotatef(-gunitem.mat25rotationz, 0.0, 0.0, 1.0);
//    	org.lwjgl.opengl.GL11.glTranslatef(-gunitem.mat25offsetx, -gunitem.mat25offsety, -gunitem.mat25offsetz);
//    }
//    org.lwjgl.opengl.GL11.glPopMatrix();
//}