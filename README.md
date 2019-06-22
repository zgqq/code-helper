# intellij-enhance
增加一些非常好用的action，得益于IDEA强大的PSI，所以本插件能够精准增强代码导航，代码修改等功能，相信你用了就离不开了

# Usages
- 配置成快捷键
  * 你可以在 Preference -> Keymap  把插件的action 插件配置成快捷键
- 结合ideavim， 比如在~/.ideavimrc 配置加入 
 `nnoremap dam :action Enhance.DeleteMethod<CR>` 就可以 在 正常模式 键入dam 删除一个方法

# Features 
## SelectMethodAction
- name: `Enhance.SelectMethod`
- 选择当前光标所在的java方法

## DeleteMethodAction
- name: `Enhance.DeleteMethod`
- 删除当前光标所在的java方法

## CopyMethodAction
- name: `Enhance.CopyMethod`
- 复制当前光标所在的java方法

## GotoMethodNameAction
- name: `Enhance.GotoMethodNameAction`
- 跳到当前方法名   
- 应用场景：
 * 有时候你需要查看哪些地方调用了光标所在方法， 而且方法很长的时候， 你可以使用 快捷键执行这个action，然后再 使用快捷键 Goto Declaration 找到调用这个方法的地方

![GotoMethodNameAction](./screenshot/gotomethodname.gif "GotoMethodNameAction")

## GotoClassNameAction
