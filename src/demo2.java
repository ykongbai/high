import java.io.*;
import java.text.Collator;
import java.util.*;
import java.util.Map.Entry;

public class demo2 {
    public static void main(String[] args) {
        // 答案
        File file = new File("E:\\DT108班-java高级考试\\Java高級笔试答案.txt");
        // 学生答案
        File file1 = new File("E:\\DT108班-java高级考试");
        // 保存分数
        Map<String, String> map = new HashMap<>();
        File[] files = file1.listFiles();
        for (File file2 : files) {
            if (file2.isDirectory()) {
                File[] files1 = file2.listFiles();
                for (File file3 : files1) {
                    // 记录分数
                    int score = 0;
                    try {
                        //  答案
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        // 学生答案
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(file3)));

                        String stu = "";
                        String tech = "";
                        while ((stu = br1.readLine()) != null && (tech = br.readLine()) != null) {
                            //  不为空
                            if (!stu.equals("") && !tech.equals("")) {
                                if (stu.equalsIgnoreCase(tech)) {
                                    score += 10;
                                } else {
                                    String[] split = null;
                                    String[] split1 = tech.split(",");
                                    if (stu.contains(",")) {
                                        split = stu.split(",");
                                    } else {
                                        split = stu.split(" ");
                                    }
                                    for (int i = 0; i < split.length; i++) {
                                        if (split[i].equalsIgnoreCase(split1[i])) {
                                            score += 2;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 添加map
                    map.put(file3.getName(), score + "分");
                }
            }
        }
        // 定义排序格式
        Comparator cmp = Collator.getInstance(Locale.CHINA);
        List<Map.Entry<String,String>> list = new ArrayList<>(map.entrySet());
        // 比较器排序
        Collections.sort(list, new Comparator<Entry<String, String>>() {
            @Override
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                if(o1.getValue().compareTo(o2.getValue()) == 0){
                    return ((Collator) cmp).compare(o1.getKey(),o2.getKey());
                }else{
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        // 存放文件夹
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\DT108班-java高级考试\\成绩表.txt")));
//            Set<Entry<String, String>> entries = map.entrySet();
            for (Entry<String, String> entry : list) {
                bw.write(entry.getKey().substring(0, entry.getKey().indexOf(".")) + ":" + entry.getValue());
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
