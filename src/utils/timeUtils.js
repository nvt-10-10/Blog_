import moment from "moment-timezone";
export function compareTime(timeString) {
  const time = moment.tz(timeString, "Asia/Ho_Chi_Minh");
  const now = moment().tz("Asia/Ho_Chi_Minh");
  const diff = now.diff(time, "minutes");
  let result = "";
  if (diff >= 10080) {
    return time.format("DD-MM-YYYY HH:mm");
  } else if (diff < 1) {
    result = "vừa mới đăng";
  } else if (diff < 60) {
    result = diff + " phút trước";
  } else if (diff < 1440) {
    result = Math.floor(diff / 60) + " giờ trước";
  } else if (diff < 10080) {
    result = Math.floor(diff / 1440) + " ngày trước";
  }
  return result;
}
