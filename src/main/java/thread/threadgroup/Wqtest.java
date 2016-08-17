package thread.threadgroup;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wq on 15/12/10.
 */
public class Wqtest {

    public static void main(String[] args) {
        Pattern p = Pattern.compile("([\\u4e00-\\u9fa5]*)[|](\\d*)");
        Matcher m = p.matcher("中文|123");
        boolean r = m.matches();
        System.out.println(r);
        if (m.find(0)) {//从第0个字符开始匹配
            System.out.println(m.group(1));
        }
//-------------------------------------------------
        String regex = "^(张|赵|王)";
        Pattern pattern = Pattern.compile(regex);
        String text = "王小丫";
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()){
            System.out.println("找到了: " + matcher.group(1));
        }else{
            System.out.println("找不到");
        }
    }
}
