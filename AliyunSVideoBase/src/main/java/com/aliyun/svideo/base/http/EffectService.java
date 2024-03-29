/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.aliyun.svideo.base.http;

import android.text.TextUtils;

import com.aliyun.jasonparse.JSONSupportImpl;
import com.aliyun.qupaiokhttp.HttpRequest;
import com.aliyun.qupaiokhttp.RequestParams;
import com.aliyun.qupaiokhttp.StringHttpRequestCallback;
import com.aliyun.svideo.sdk.external.struct.form.FontForm;
import com.aliyun.svideo.sdk.external.struct.form.IMVForm;
import com.aliyun.svideo.sdk.external.struct.form.PasterForm;
import com.aliyun.svideo.sdk.external.struct.form.PreviewPasterForm;
import com.aliyun.svideo.sdk.external.struct.form.ResourceForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EffectService {
    private Gson mGson = new GsonBuilder().disableHtmlEscaping().create();
    public static final int EFFECT_TEXT = 1;        //字体
    public static final int EFFECT_PASTER = 2;      //动图
    public static final int EFFECT_MV = 3;          //MV
    public static final int EFFECT_FILTER = 4;      //滤镜
    public static final int EFFECT_MUSIC = 5;       //音乐
    public static final int EFFECT_CAPTION = 6;     //字幕
    public static final int EFFECT_FACE_PASTER = 7; //人脸动图
    public static final int EFFECT_IMG = 8;         //静态贴纸
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public static final String BASE_URL = "https://alivc-demo.aliyuncs.com";//http://47.102.219.109:8080
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public void loadEffectPaster(final HttpCallback<List<ResourceForm>> callback) {
        String url = new StringBuilder(BASE_URL).append("/resource/getPasterInfo").toString();
        RequestParams params = new RequestParams();
        params.addFormDataPart("type", 2);
        HttpRequest.get(url, params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<ResourceForm> resourceList = mGson.fromJson(jsonArray.toString(), new TypeToken<List<ResourceForm>>() {
                    } .getType());
                    if (callback != null) {
                        callback.onSuccess(resourceList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        });

    }
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public void getPasterListById(int id, final HttpCallback<List<PasterForm>> callback) {
        StringBuilder resUrl = new StringBuilder();
        resUrl.append(BASE_URL).append("/resource/getPasterList");
        RequestParams params = new RequestParams();
        params.addFormDataPart("type", 2);
        params.addFormDataPart("pasterId", id);
        HttpRequest.get(resUrl.toString(), params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<PasterForm> resourceList = mGson.fromJson(jsonArray.toString(), new TypeToken<List<PasterForm>>() {
                    } .getType());
                    if (callback != null) {
                        callback.onSuccess(resourceList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        } );
    }
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public void getCaptionListById(int id, final HttpCallback<List<PasterForm>> callback) {
        StringBuilder resUrl = new StringBuilder();
        resUrl.append(BASE_URL).append("/resource/getTextPasterList");
        RequestParams params = new RequestParams();
        params.addFormDataPart("textPasterId", id);
        HttpRequest.get(resUrl.toString(), params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<PasterForm> resourceList = mGson.fromJson(jsonArray.toString(), new TypeToken<List<PasterForm>>() {
                    } .getType());
                    if (callback != null) {
                        callback.onSuccess(resourceList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        } );
    }
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     * 通过字体id获取字体信息
     */
    public void getFontById(int id, final HttpCallback<FontForm> callback) {
        StringBuilder resUrl = new StringBuilder();
        resUrl.append(BASE_URL).append("/resource/getFont");
        RequestParams params = new RequestParams();
        params.addFormDataPart("fontId", id);
        HttpRequest.get(resUrl.toString(), params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONSupportImpl jsonSupport = new JSONSupportImpl();
                    FontForm fontForm = jsonSupport.readValue(data.toString(), FontForm.class);
                    if (callback != null) {
                        callback.onSuccess(fontForm);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        });
    }
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     * 获取前置动图
     */
    public void loadFrontEffectPaster(final HttpCallback<List<PreviewPasterForm>> callback) {
        StringBuilder resUrl = new StringBuilder();
        resUrl.append(BASE_URL).append("/resource/getFrontPasterList");
        HttpRequest.get(resUrl.toString(), new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                JSONSupportImpl jsonSupport = new JSONSupportImpl();
                List<PreviewPasterForm> resources;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    resources = jsonSupport.readListValue(jsonArray.toString(),
                    new TypeToken<List<PreviewPasterForm>>() {
                    } .getType());

                    if (callback != null) {
                        callback.onSuccess(resources);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        } );

    }
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public void loadEffectMv(
        final HttpCallback<List<IMVForm>> callback) {
        String url = new StringBuilder(BASE_URL).append("/resource/getMv").toString();
        HttpRequest.get(url, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<IMVForm> resourceList = mGson.fromJson(jsonArray.toString(), new TypeToken<List<IMVForm>>() {
                    } .getType());
                    if (callback != null) {
                        callback.onSuccess(resourceList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        });


    }
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public void loadEffectCaption(final HttpCallback<List<ResourceForm>> callback) {
        String url = new StringBuilder(BASE_URL).append("/resource/getTextPaster").toString();
        HttpRequest.get(url, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    List<ResourceForm> resourceList = mGson.fromJson(jsonArray.toString(), new TypeToken<List<ResourceForm>>() {
                    } .getType());
                    if (callback != null) {
                        callback.onSuccess(resourceList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        });

    }

    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public void loadingMusicData(int pageNo, int pageSize, String keyWord, final HttpCallback<List<MusicFileBean>> callback) {

        String url = BASE_URL + "/music/getRecommendMusic";

        RequestParams params = new RequestParams();
        params.addFormDataPart("pageNo", pageNo);
        params.addFormDataPart("pageSize", pageSize);
        if (!TextUtils.isEmpty(keyWord)) {
            params.addFormDataPart("keyWords", keyWord);
        }
        HttpRequest.get(url, params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = dataObject.getJSONArray("musicList");
                    List<MusicBean> resourceList = mGson.fromJson(jsonArray.toString(), new TypeToken<List<MusicBean>>() {
                    } .getType());
                    if (callback != null) {
                        List<MusicFileBean> musicFileBeanList = new ArrayList<>();
                        for (MusicBean musicBean : resourceList) {
                            musicFileBeanList.add(new MusicFileBean(
                                musicBean.getTitle(),
                                musicBean.getArtistName(),
                                musicBean.getMusicId(),
                                musicBean.getImage()
                            ));
                        }
                        callback.onSuccess(musicFileBeanList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        });
    }
    /**
     * 素材分发服务为官方demo演示使用，无法达到商业化使用程度。请自行搭建相关的服务
     */
    public void getMusicDownloadUrlById(String musicId, final HttpCallback<String> callback) {
        String url = BASE_URL + "/music/getPlayPath";
        final RequestParams params = new RequestParams();
        params.addFormDataPart("musicId", musicId);
        HttpRequest.get(url, params, new StringHttpRequestCallback() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    String playPath = dataObject.getString("playPath");
                    if (callback != null) {
                        callback.onSuccess(playPath);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if (callback != null) {
                    callback.onFailure(new Throwable(msg));
                }
            }
        });

    }
}
