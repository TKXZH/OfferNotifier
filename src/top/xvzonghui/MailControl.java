package top.xvzonghui;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


public class MailControl {
	Timer timer;
	public MailControl() throws Exception {
		timer = new Timer();
		timer.schedule(new MailTask(DataProcessor.refresh(),"宣讲提醒"), this.getTimeNight(),24 * 60 * 60 * 1000);
//		timer.schedule(new MailTask("天气炎热，注意避暑，爸爸爱你。——————————该邮件由一台24小时运行的云主机发送Designed By XZH", "已经是中午了，老板"), this.getTimeAfternoon(),24 * 60 * 60 * 1000);
//		timer.schedule(new MailTask("早点睡觉，儿子。——————————该邮件由一台24小时运行的云主机发送Designed By XZH", "已经是晚上了，老板"), this.getTimeNight(),24 * 60 * 60 * 1000);
	}
	
	private Date getTimeAfternoon() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 33);
		Date date = calendar.getTime();
		if(date.before(new Date())) {
			date = this.addDay(date, 1);
		}
		return date;
	}
	private Date getTimeMorning() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 07);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		if(date.before(new Date())) {
			date = this.addDay(date, 1);
		}
		return date;
	}
	private Date getTimeNight( ) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 01);
		calendar.set(Calendar.SECOND, 15);
		Date date = calendar.getTime();
		if(date.before(new Date())) {
			date = this.addDay(date, 1);
		}
		return date;
	}
	public Date addDay(Date date, int num) {  
        Calendar startDT = Calendar.getInstance();  
        startDT.setTime(date);  
        startDT.add(Calendar.DAY_OF_MONTH, num);  
        return startDT.getTime();  
    }  
	
	public static void main(String args[]) throws Exception {
		new MailControl();
		new MailTask(DataProcessor.refresh(),"明日宣讲信息").run();
	}
}
