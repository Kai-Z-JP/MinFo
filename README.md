# MinFo
MinFo Is not Normal FOntrenderer

## これはなに?
TTFファイルまたはフォント名から専用FontRendererを生成するAPIです。

## 動作確認済み環境
- Minecraft 1.7.10
- Forge 10.13.4.1614
- Windows 10 build 20270.fe_release.201124-1440

## 使用方法
```Java
import jp.kaiz.minfo.api

FontRenderer fontRenderer1 = new MinFoCustomFontRenderer((ResourceLocation)TTFファイル, (int)フォントサイズ);
FontRenderer fontRenderer2 = new MinFoCustomFontRenderer((String)フォント名, (int)フォントサイズ);
```

```JavaScript
importPackage(Packages.jp.kaiz.minfo.api)

var fontRenderer1 = new MinFoCustomFontRenderer((ResourceLocation)TTFファイル, (int)フォントサイズ);
var fontRenderer2 = new MinFoCustomFontRenderer((String)フォント名, (int)フォントサイズ);
```
