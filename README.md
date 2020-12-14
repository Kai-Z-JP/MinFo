# MinFo
MinFo Is not Normal FOntrenderer

## これはなに?
TTFファイルまたはフォント名から専用FontRendererを生成するAPIです。

## 動作確認済み環境
- Minecraft 1.7.10
- Forge 10.13.4.1614
- Windows 10 build 20270.fe_release.201124-1440

## 使用例
### JavaScript
```JavaScript
importPackage(Packages.jp.kaiz.minfo.api)

var fontRenderer = null;

function getFontRenderer() {
//フォント名指定の場合 TTFファイル指定の場合はResourceLocationを第一引数へ
    return fontRenderer == null ? fontRenderer = new MinFoCustomFontRenderer("Meiryo UI", 32) : fontRenderer;
}

///renderの一番最後に突っ込む
function renderFont(entity, pass, par3) {
    GL11.glPushMatrix();
    getFontRenderer().func_78276_b("テスト", 0, 0, 0xFFFFFF);
    GL11.glPopMatrix();
}
```
