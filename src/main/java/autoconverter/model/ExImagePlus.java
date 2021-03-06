/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoconverter.model;

import autoconverter.controller.ApplicationController;
import autoconverter.controller.AutoConverterUtils;
import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author yfujita
 */
public class ExImagePlus extends ImagePlus {
	// コンストラクタ

	public ExImagePlus(){
		super();
	}
	public ExImagePlus(String title, java.awt.Image image){
		super(title, image);
		this.initParameter(new File(title));
	}
	public ExImagePlus(String title, ImageProcessor ip){
		super(title, ip);
		this.initParameter(new File(title));
	}
	public ExImagePlus(String path){
		super(path);
		this.initParameter(new File(path));
	}
	public ExImagePlus(String title, ImageStack stack){
		super(title, stack);
		this.initParameter(new File(title));
	}
	/**
	 * パスを指定してインスタンスを作製する.
	 *
	 * @param _f
	 */
	public ExImagePlus(File _f) throws IllegalArgumentException, IllegalStateException {
		super(_f.getAbsolutePath());
		this.initParameter(_f);
	}

	public void initParameter(File _f){
		//this.keys = new ArrayList(Arrays.asList("WELL", "NUM", "POS", "ZPOS", "TIME", "FILTER"));

		if (_f == null || !_f.canRead()) {
			return;
		}
		params = new HashMap();
		this.directory = "";
		this.shotID = "";
		this.imageID = "";
		this.appCtrl = ApplicationController.getInstance();
		this.file = _f;
		this.directory = _f.getParent();
		
		Pattern filePattern = appCtrl.getFilePattern();
		Matcher matcher = filePattern.matcher(_f.getName());
		if (matcher.matches()) {
			for(String key: ExImagePlus.keys){
				try{
					if(matcher.group(key) == null){
						params.put(key, "");
					} else {
						params.put(key, matcher.group(key));
					}
				} catch(IllegalArgumentException e){
					params.put(key, ""); // "" を入れとかないとエラーになるので...
				}
				
			}
		} else {
			logger.warning(_f.getAbsolutePath() + " is not supported.");
		}
		// フィルタ名が""だと設定が保存されいないので、フィルタ名無しの時の名前を追加.
		if( params.get("FILTER") == null ){
			params.put("FILTER", "NOSPECIFIED");
		}
		if( params.get("FILTER").equals("") ){
			params.put("FILTER", "NOSPECIFIED");
		}
		this.shotID = ApplicationController.createShotID(directory, params.get("WELL"), params.get("POS"),  params.get("ZPOS"), params.get("TIME"));
		this.imageID = ApplicationController.createImageID(directory, params.get("WELL"), params.get("POS"),  params.get("ZPOS"), params.get("TIME"), params.get("FILTER"));
		this.file = _f;
		
	}
	
	
	// static メソッドとか
	private static final Logger logger = AutoConverterUtils.getLogger();
	public static final int TYPE_CELAVIEW = 1;
	public static final int TYPE_IX = 2;
	public static final int TYPE_UNKNOWN = 3;
	public static final String NO_SPECIFIED = "";

	/**
	 * イメージの保存先ディレクトリ
	 */
	private String directory = null;
	/**
	 * shotID = [directory, wellName, well, position, slice, time].join("-")
	 */
	private String shotID = null;
	/**
	 * imageID = [directory, wellName, well, position, slice, time,
	 * filter].join("-")
	 */
	private String imageID = null;
	/**
	 * 実態のファイル.
	 */
	private File file = null;

	/**
	 * ApplicationController のinstance
	 */
	private ApplicationController appCtrl = null;

	/**
	 * ファイルのパラメータ(well, time 等)を保存するHashMap
	 */
	private HashMap<String, String> params = null;

	private static final ArrayList<String> keys = new ArrayList(Arrays.asList("WELL", "NUM", "POS", "ZPOS", "TIME", "FILTER"));;


	/**
	 * imageIDが一致するか調べる. nullが与えられると、trueを返す.
	 *
	 * @param _imageID
	 * @return
	 */
	public boolean isImageID(String _imageID) {
		if (_imageID == null) {
			return true;
		}
		return this.getImageID().equals(_imageID);
	}

	/**
	 * shotIDが一致するか調べる. nullが与えられると、trueを返す.
	 *
	 * @param _shotID
	 * @return
	 */
	public boolean isShotID(String _shotID) {
		if (_shotID == null) {
			return true;
		}
		return this.getShotID().equals(_shotID);
	}

	/**
	 * directory が一致するか調べる. nullが与えられると、trueを返す.
	 *
	 * @param _directory
	 * @return
	 */
	public boolean isDirectory(String _directory) {
		if (_directory == null) {
			return true;
		}
		return this.getDirectory().equals(_directory);
	}

	/**
	 * well が一致するか調べる. nullが与えられると、trueを返す.
	 *
	 * @param _well
	 * @return
	 */
	public boolean isWell(String _well) {
		if (_well == CaptureImage.NO_SPECIFIED) {
			return true;
		}
		return this.getWell() == _well;
	}

	/**
	 * wellName が一致するか調べる. nullが与えられると、trueを返す.
	 *
	 * @param _wellName
	 * @return
	 */
	public boolean isWellName(String _wellName) {
		if (_wellName == null) {
			return true;
		}
		return this.getWellName().equals(_wellName);
	}

	/**
	 * position が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
	 *
	 * @param _position
	 * @return
	 */
	public boolean isPosition(String _position) {
		if (_position == CaptureImage.NO_SPECIFIED) {
			return true;
		}
		return this.getPosition() == _position;
	}

	/**
	 * slice が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
	 *
	 * @param _slice
	 * @return
	 */
	public boolean isSlice(String _slice) {
		if (_slice == CaptureImage.NO_SPECIFIED) {
			return true;
		}
		return this.getZPos() == _slice;
	}

	/**
	 * time が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
	 *
	 * @param _time
	 * @return
	 */
	public boolean isTime(String _time) {
		if (_time == CaptureImage.NO_SPECIFIED) {
			return true;
		}
		return this.getTime() == _time;
	}

	/**
	 * filter が一致するか調べる. NO_SPECIFIEDが与えられると、trueを返す.
	 *
	 * @param _filter
	 * @return
	 */
	public boolean isFilter(String _filter) {
		if (_filter == null) {
			return true;
		}
		return this.getFilter().equals(_filter);
	}

	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @return the wellName
	 */
	public String getWellName() {
		return params.get("WELL");
	}

	/**
	 * @return the well number
	 */
	public String getWell() {
		return params.get("NUM");
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return params.get("POS");
	}

	/**
	 * @return the slice
	 */
	public String getZPos() {
		return params.get("ZPOS");
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return params.get("TIME");
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return params.get("FILTER");
	}

	/**
	 * @return the shotID
	 */
	public String getShotID() {
		return shotID;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return the imageID
	 */
	public String getImageID() {
		return imageID;
	}

	String getInfo() {
		StringBuilder sb;
		sb = new StringBuilder("");
		sb.append("getImageID() = ").append(this.getImageID()).append("\n");
		sb.append("getDirectory() = ").append(this.getDirectory()).append("\n");
		sb.append("getFilter() = ").append(this.getFilter()).append("\n");
		sb.append("getPosition() = ").append(this.getPosition()).append("\n");
		sb.append("getShotID() = ").append(this.getShotID()).append("\n");
		sb.append("getTime() = ").append(this.getTime()).append("\n");
		sb.append("getWell() = ").append(this.getWell()).append("\n");
		sb.append("getWellName() = ").append(this.getWellName()).append("\n");
		return sb.toString();

	}

	public ImagePlus getImagePlus(){
		ImagePlus imp = new ImagePlus(this.getFile().getAbsolutePath());
		return imp;
		//ImagePlus imp = new ImagePlus(_cimg.getFile().getAbsolutePath());
	}
}
