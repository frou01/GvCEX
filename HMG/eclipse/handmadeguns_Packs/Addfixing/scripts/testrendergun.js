function renderFirst(renderinstance,gunitem,gunstack,model,entity,type,data){
	gunitem.gunInfo.userenderscript = false;
	renderinstance.renderItem(type,gunstack,data);
	gunitem.gunInfo.userenderscript = true;
}
function renderThird(renderinstance,gunitem,gunstack,model,entity,type,data){
	gunitem.gunInfo.userenderscript = false;
	renderinstance.renderItem(type,gunstack,data);
	gunitem.gunInfo.userenderscript = true;
}
function GunModelRender_New(renderinstance,gunitem,gunstack,model,entity,type,data){
	return false;//これをtrueにすると通常のパーツ描画が止まる
}

function GunModelRender_New_post(renderinstance,gunitem,gunstack,model,entity,type,data){
}

function update_onplayer(ins, itemstack, nbt, entity){
}
function update_all(ins, itemstack, nbt, entity){
}
function update_onmaid(ins, itemstack, nbt, entity){
}
function update_onliving(ins, itemstack, nbt, entity){
}
function update_onplacedGun(ins, itemstack, nbt, entity){
}
function proceedcock(ins, itemstack, nbt, entity){
}
function startreload(ins, itemstack, nbt, entity){
}
function prefire(ins, itemstack, nbt, entity){
}
function fireout(ins, itemstack, nbt, entity){
}
function proceedreload(ins, itemstack, nbt, entity){
}
function GUI_rendering_2D(ins , gunItem , gunstack){
	return true;//これをtrueにすると通常の情報表示GUIが消える
}
function GUI_rendering_3D(ins , gunItem , gunstack){
	return true;//これをtrueにすると通常のマーカー/スコープテクスチャが消える
}
function preInit(event){
}