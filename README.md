# intellij-enhance
增加一些非常好用的action

# Usages
- 配置成快捷键
  * 你可以在 Preference -> Keymap  把插件的action 插件配置成快捷键
- 结合ideavim， 比如在~/.ideavimrc 配置加入 
 `nnoremap dam :action Enhance.DeleteMethod<CR>` 就可以 在 正常模式 键入dam 删除一个方法

# Features 
## SelectMethod action
- name: `Enhance.SelectMethod`
- 选择当前光标所在的java方法

## DeleteMethod action
- name: `Enhance.DeleteMethod`
- 删除当前光标所在的java方法
