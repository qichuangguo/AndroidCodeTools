package com.qfxu.image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

public class HttpURLConnHelper {
	/**
	 * 浣滅敤锛氬疄鐜扮綉缁滆闂枃浠讹紝灏嗚幏鍙栧埌鏁版嵁鍌ㄥ瓨鍦ㄦ枃浠舵祦涓�
	 * 
	 * @param url
	 *            锛氳闂綉缁滅殑url鍦板潃
	 * @return inputstream
	 */
	public static InputStream loadFileFromURL(String urlString) {
		BufferedInputStream bis = null;
		HttpURLConnection httpConn = null;
		try {
			// 鍒涘缓url瀵硅薄
			URL urlObj = new URL(urlString);
			// 鍒涘缓HttpURLConnection瀵硅薄锛岄�杩囪繖涓璞℃墦寮�窡杩滅▼鏈嶅姟鍣ㄤ箣闂寸殑杩炴帴
			httpConn = (HttpURLConnection) urlObj.openConnection();

			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			httpConn.setConnectTimeout(5000);
			httpConn.connect();

			// 鍒ゆ柇璺熸湇鍔″櫒鐨勮繛鎺ョ姸鎬併�濡傛灉鏄�00锛屽垯璇存槑杩炴帴姝ｅ父锛屾湇鍔″櫒鏈夊搷搴�
			if (httpConn.getResponseCode() == 200) {
				// 鏈嶅姟鍣ㄦ湁鍝嶅簲鍚庯紝浼氬皢璁块棶鐨剈rl椤甸潰涓殑鍐呭鏀捐繘inputStream涓紝浣跨敤httpConn灏卞彲浠ヨ幏鍙栧埌杩欎釜瀛楄妭娴�
				bis = new BufferedInputStream(httpConn.getInputStream());
				return bis;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 瀵规祦瀵硅薄杩涜鍏抽棴锛屽Http杩炴帴瀵硅薄杩涜鍏抽棴銆備互渚块噴鏀捐祫婧愩�
				bis.close();
				httpConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 浣滅敤锛氬疄鐜扮綉缁滆闂枃浠讹紝灏嗚幏鍙栧埌鐨勬暟鎹瓨鍦ㄥ瓧鑺傛暟缁勪腑
	 * 
	 * @param url
	 *            锛氳闂綉缁滅殑url鍦板潃
	 * @return byte[]
	 */
	public static byte[] loadByteFromURL(String url) {
		HttpURLConnection httpConn = null;
		BufferedInputStream bis = null;
		try {
			URL urlObj = new URL(url);
			httpConn = (HttpURLConnection) urlObj.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(5000);
			httpConn.connect();

			if (httpConn.getResponseCode() == 200) {
				bis = new BufferedInputStream(httpConn.getInputStream());
				return streamToByte(bis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				httpConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 浣滅敤锛氬疄鐜扮綉缁滆闂枃浠讹紝灏嗚幏鍙栧埌鐨勬暟鎹繚瀛樺湪鎸囧畾鐩綍涓�
	 * 
	 * @param url
	 *            锛氳闂綉缁滅殑url鍦板潃
	 * @return byte[]
	 */
	public static boolean saveFileFromURL(String url, File destFile) {
		HttpURLConnection httpConn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(destFile));
			URL urlObj = new URL(url);
			httpConn = (HttpURLConnection) urlObj.openConnection();
			httpConn.setRequestMethod("GET");
			httpConn.setDoInput(true);
			httpConn.setConnectTimeout(5000);
			httpConn.connect();

			if (httpConn.getResponseCode() == 200) {
				bis = new BufferedInputStream(httpConn.getInputStream());
				int c = 0;
				byte[] buffer = new byte[8 * 1024];
				while ((c = bis.read(buffer)) != -1) {
					bos.write(buffer, 0, c);
					bos.flush();
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				httpConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 浣滅敤锛氬疄鐜扮綉缁滆闂枃浠讹紝鍏堢粰鏈嶅姟鍣ㄩ�杩団�POST鈥濇柟寮忔彁浜ゆ暟鎹紝鍐嶈繑鍥炵浉搴旂殑鏁版嵁
	 * 
	 * @param url
	 *            锛氳闂綉缁滅殑url鍦板潃
	 * @param params
	 *            锛氳闂畊rl鏃讹紝闇�浼犻�缁欐湇鍔″櫒鐨勫弬鏁般�鏍煎紡涓猴細username=wangxiangjun&password=abcde&
	 *            qq=32432432
	 *            涓轰簡闃叉浼犱腑鏂囧弬鏁版椂鍑虹幇缂栫爜闂銆傞噰鐢║RLEncoder.encode()瀵瑰惈涓枃鐨勫瓧绗︿覆杩涜缂栫爜澶勭悊銆�
	 *            鏈嶅姟鍣ㄧ浼氳嚜鍔ㄥ杩涜杩囩紪鐮佺殑瀛楃涓茶繘琛宒ecode()瑙ｇ爜銆�
	 * @return byte[]
	 */
	public static byte[] doPostSubmit(String url, String params) {
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpConn = null;
		try {
			URL urlObj = new URL(url);
			httpConn = (HttpURLConnection) urlObj.openConnection();

			// 濡傛灉閫氳繃post鏂瑰紡缁欐湇鍔″櫒浼犻�鏁版嵁锛岄偅涔坰etDoOutput()蹇呴』璁剧疆涓簍rue銆傚惁鍒欎細寮傚父銆�
			// 榛樿鎯呭喌涓媠etDoOutput()涓篺alse銆�
			// 鍏跺疄涔熷簲璇ヨ缃畇etDoInput()锛屼絾鏄洜涓簊etDoInput()榛樿涓簍rue銆傛墍浠ヤ笉涓�畾瑕佸啓銆�

			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			httpConn.setConnectTimeout(5 * 1000);
			// 璁剧疆璇锋眰鏂瑰紡銆傝姹傛柟寮忔湁涓ょ锛歅OST/GET銆傛敞鎰忚鍏ㄥぇ鍐欍�
			// POST浼犻�鏁版嵁閲忓ぇ锛屾暟鎹洿瀹夊叏锛屽湴鍧�爮涓笉浼氭樉绀轰紶杈撴暟鎹�
			// 鑰孏ET浼氬皢浼犺緭鐨勬暟鎹毚闇插湪鍦板潃鏍忎腑锛屼紶杈撶殑鏁版嵁閲忓ぇ灏忔湁闄愬埗锛岀浉瀵筆OST涓嶅瀹夊叏銆備絾鏄疓ET鎿嶄綔鐏垫椿绠�究銆�

			// 鍒ゆ柇鏄惁瑕佸線鏈嶅姟鍣ㄤ紶閫掑弬鏁般�濡傛灉涓嶄紶閫掑弬鏁帮紝閭ｄ箞灏辨病鏈夊繀瑕佷娇鐢ㄨ緭鍑烘祦浜嗐�
			if (params != null) {
				byte[] data = params.getBytes();
				bos = new BufferedOutputStream(httpConn.getOutputStream());
				bos.write(data);
				bos.flush();
			}
			// 鍒ゆ柇璁块棶缃戠粶鐨勮繛鎺ョ姸鎬�
			if (httpConn.getResponseCode() == 200) {
				bis = new BufferedInputStream(httpConn.getInputStream());
				// 灏嗚幏鍙栧埌鐨勮緭鍏ユ祦杞垚瀛楄妭鏁扮粍
				return streamToByte(bis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				httpConn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 缃戠粶璁块棶锛屼笂浼犻檮浠�浜斾釜鍙傛暟锛�1銆丼tring url锛氭寚瀹氳〃鍗曟彁浜ょ殑url鍦板潃 2銆丮ap<String, String>
	 * map锛氬皢涓婁紶鎺т欢涔嬪鐨勫叾浠栨帶浠剁殑鏁版嵁淇℃伅瀛樺叆map瀵硅薄 3銆丼tring filePath锛氭寚瀹氳涓婁紶鍒版湇鍔″櫒鐨勬枃浠剁殑瀹㈡埛绔矾寰�
	 * 4銆乥yte[] body_data锛氳幏鍙栧埌瑕佷笂浼犵殑鏂囦欢鐨勮緭鍏ユ祦淇℃伅锛岄�杩嘊yteArrayOutputStream娴佽浆鎴恇yte[]
	 * 5銆丼tring charset锛氳缃瓧绗﹂泦
	 */
	public static String doPostSubmitBody(String url, Map<String, String> map,
			String filePath, byte[] body_data, String charset) {
		// 璁剧疆涓変釜甯哥敤瀛楃涓插父閲忥細鎹㈣銆佸墠缂��鍒嗙晫绾匡紙NEWLINE銆丳REFIX銆丅OUNDARY锛夛紱
		final String NEWLINE = "\r\n";
		final String PREFIX = "--";
		final String BOUNDARY = "#";// 鍙栦唬---------------------------7df3a01e37070c
		HttpURLConnection httpConn = null;
		BufferedInputStream bis = null;
		DataOutputStream dos = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 瀹炰緥鍖朥RL瀵硅薄銆傝皟鐢║RL鏈夊弬鏋勯�鏂规硶锛屽弬鏁版槸涓�釜url鍦板潃锛�
			URL urlObj = new URL(url);
			// 璋冪敤URL瀵硅薄鐨刼penConnection()鏂规硶锛屽垱寤篐ttpURLConnection瀵硅薄锛�
			httpConn = (HttpURLConnection) urlObj.openConnection();
			// 璋冪敤HttpURLConnection瀵硅薄setDoOutput(true)銆乻etDoInput(true)銆乻etRequestMethod("POST")锛�
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("POST");
			// 璁剧疆Http璇锋眰澶翠俊鎭紱锛圓ccept銆丆onnection銆丄ccept-Encoding銆丆ache-Control銆丆ontent-Type銆乁ser-Agent锛�
			httpConn.setUseCaches(false);
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			httpConn.setRequestProperty("Cache-Control", "no-cache");
			httpConn.setRequestProperty("Content-Type",
					"multipart/form-data; boundary=" + BOUNDARY);
			httpConn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30)");
			// 璋冪敤HttpURLConnection瀵硅薄鐨刢onnect()鏂规硶锛屽缓绔嬩笌鏈嶅姟鍣ㄧ殑鐪熷疄杩炴帴锛�
			httpConn.connect();

			// 璋冪敤HttpURLConnection瀵硅薄鐨刧etOutputStream()鏂规硶鏋勫缓杈撳嚭娴佸璞★紱
			dos = new DataOutputStream(httpConn.getOutputStream());
			// 鑾峰彇琛ㄥ崟涓笂浼犳帶浠朵箣澶栫殑鎺т欢鏁版嵁锛屽啓鍏ュ埌杈撳嚭娴佸璞★紙鏍规嵁HttpWatch鎻愮ず鐨勬祦淇℃伅鎷煎噾瀛楃涓诧級锛�
			if (map != null && !map.isEmpty()) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey();
					String value = map.get(key);
					dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
					dos.writeBytes("Content-Disposition: form-data; "
							+ "name=\"" + key + "\"" + NEWLINE);
					dos.writeBytes(NEWLINE);
					dos.writeBytes(URLEncoder.encode(value.toString(), charset));
					// 鎴栬�鍐欐垚锛歞os.write(value.toString().getBytes(charset));
					dos.writeBytes(NEWLINE);
				}
			}

			// 鑾峰彇琛ㄥ崟涓笂浼犳帶浠剁殑鏁版嵁锛屽啓鍏ュ埌杈撳嚭娴佸璞★紙鏍规嵁HttpWatch鎻愮ず鐨勬祦淇℃伅鎷煎噾瀛楃涓诧級锛�
			if (body_data != null && body_data.length > 0) {
				dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
				String fileName = filePath.substring(filePath
						.lastIndexOf(File.separatorChar) + 1);
				dos.writeBytes("Content-Disposition: form-data; " + "name=\""
						+ "uploadFile" + "\"" + "; filename=\"" + fileName
						+ "\"" + NEWLINE);
				dos.writeBytes(NEWLINE);
				dos.write(body_data);
				dos.writeBytes(NEWLINE);
			}
			dos.writeBytes(PREFIX + BOUNDARY + PREFIX + NEWLINE);
			dos.flush();

			// 璋冪敤HttpURLConnection瀵硅薄鐨刧etInputStream()鏂规硶鏋勫缓杈撳叆娴佸璞★紱
			byte[] buffer = new byte[8 * 1024];
			int c = 0;
			// 璋冪敤HttpURLConnection瀵硅薄鐨刧etResponseCode()鑾峰彇瀹㈡埛绔笌鏈嶅姟鍣ㄧ鐨勮繛鎺ョ姸鎬佺爜銆傚鏋滄槸200锛屽垯鎵ц浠ヤ笅鎿嶄綔锛屽惁鍒欒繑鍥瀗ull锛�
			if (httpConn.getResponseCode() == 200) {
				bis = new BufferedInputStream(httpConn.getInputStream());
				while ((c = bis.read(buffer)) != -1) {
					baos.write(buffer, 0, c);
					baos.flush();
				}
			}
			// 灏嗚緭鍏ユ祦杞垚瀛楄妭鏁扮粍锛岃繑鍥炵粰瀹㈡埛绔�
			return new String(baos.toByteArray(), charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null) {
					dos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (baos != null) {
					baos.close();
				}
				httpConn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] streamToByte(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c = 0;
		byte[] buffer = new byte[8 * 1024];
		try {
			while ((c = is.read(buffer)) != -1) {
				baos.write(buffer, 0, c);
				baos.flush();
			}
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
