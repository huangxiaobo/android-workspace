package com.crawl.element;

/**
 * Created by hxb on 2018/4/1.
 */
public class User {

    public String name;                 // 呢称
    public String id;                   // id
    public String avatarUrl;            // 头像
    public String urlToken;             // 用户标识
    public String userType;             // 用户类型
    public String headline;             // 用户简介
    public String url;                  // 个人主页
    public int gender;                  // 性别
    public int voteupCount = 0;         // 赞同次数
    public int thankedCount = 0;        // 感谢次数
    public int followerCount = 0;       // 被关注人数
    public int followingCount = 0;      // 关注人数
    public int answerCount = 0;         // 回答次数
    public int articlesCount = 0;       // 文章数

    public int getFollowees() {
        return followingCount;
    }

    public String toString() {
        return String.format(
            "[name: %s, id: %s gender: %s, headline: %s, url: %s]",
            name, urlToken, gender, headline, url
        );
    }
}


/*
"users": {
  "huang-liao-57": {
    "isFollowed": false,
    "educations": [
      {
        "school": {
          "introduction": "<p>一所位于湖北省武汉市的中国顶尖综合研究型大学。是<b>国家“双一流”工程、世界一流大学建设高校。</b></p>",
          "avatarUrl": "https://pic2.zhimg.com/v2-0b654f985175bb8a276263f28fa1f6d1_is.jpg",
          "name": "华中科技大学",
          "url": "http://www.zhihu.com/api/v4/topics/19587094",
          "type": "topic",
          "excerpt": "一所位于湖北省武汉市的中国顶尖综合研究型大学。是国家“双一流”工程、世界一流大学建设高校。",
          "id": "19587094"
        }
      }
    ],
    "followingCount": 22,
    "voteFromCount": 0,
    "userType": "people",
    "includedText": "",
    "pinsCount": 0,
    "isFollowing": false,
    "isPrivacyProtected": false,
    "accountStatus": [],
    "includedArticlesCount": 0,
    "isForceRenamed": false,
    "id": "ec1c55830f2868bed7c25b04c41530a1",
    "favoriteCount": 7,
    "voteupCount": 3,
    "commercialQuestionCount": 0,
    "isBlocking": false,
    "followingColumnsCount": 4,
    "headline": "游戏开发/Python/云计算/拖延症晚期患者",
    "urlToken": "huang-liao-57",
    "participatedLiveCount": 0,
    "isAdvertiser": false,
    "followingFavlistsCount": 43,
    "favoritedCount": 4,
    "isOrg": false,
    "followerCount": 3,
    "employments": [],
    "type": "people",
    "avatarHue": "0x5d6055",
    "avatarUrlTemplate": "https://pic3.zhimg.com/v2-a6c260a14026dcb2371988cdac054c67_{size}.jpg",
    "followingTopicCount": 27,
    "description": "这个人很懒，什么都没留下……",
    "business": {
      "introduction": "互联网（英语：Internet），是网络与网络之间所串连成的庞大网络，这些网络以一组标准的网络TCP/IP协议族相连，链接全世界几十亿个设备，形成逻辑上的单一巨大国际网络。这是一个网络的网络，它是由从地方到全球范围内几百万个私人的，学术界的，企业的和政府的网络所构成，通过电子，无线和光纤网络技术等等一系列广泛的技术联系在一起.这种将计算机网络互相联接在一起的方法可称作“网络互联”，在这基础上发展出覆盖全世界的全球性互联网络称互联网，即是互相连接一起的网络。",
      "avatarUrl": "https://pic2.zhimg.com/f07808da5625fef3607f8b75b770349f_is.jpg",
      "name": "互联网",
      "url": "http://www.zhihu.com/api/v4/topics/19550517",
      "type": "topic",
      "excerpt": "互联网（英语：Internet），是网络与网络之间所串连成的庞大网络，这些网络以一组标准的网络TCP/IP协议族相连，链接全世界几十亿个设备，形成逻辑上的单一巨大国际网络。这是一个网络的网络，它是由从地方到全球范围内几百万个私人的，学术界的，企业的和政府的网络所构成，通过电子，无线和光纤网络技术等等一系列广泛的技术联系在一起.这种将计算机网络互相联接在一起的方法可称作“网络互联”，在这基础上发展出覆盖全世界的…",
      "id": "19550517"
    },
    "avatarUrl": "https://pic3.zhimg.com/v2-a6c260a14026dcb2371988cdac054c67_is.jpg",
    "columnsCount": 0,
    "hostedLiveCount": 0,
    "isActive": 1,
    "thankToCount": 0,
    "mutualFolloweesCount": 0,
    "coverUrl": "",
    "thankFromCount": 0,
    "voteToCount": 0,
    "isBlocked": false,
    "answerCount": 8,
    "allowMessage": false,
    "articlesCount": 0,
    "name": "黄了",
    "questionCount": 1,
    "locations": [
      {
        "introduction": "<p>东南形胜，三吴都会，钱塘自古繁华，烟柳画桥，风帘翠幕，参差十万人家。云树绕堤沙，怒涛卷霜雪，天堑无涯。市列珠玑，户盈罗绮，竞豪奢。<br>重湖叠巘清嘉。有三秋桂子，十里荷花。羌管弄晴，菱歌泛夜，嬉嬉钓叟莲娃。千骑拥高牙。乘醉听箫鼓，吟赏烟霞。异日图将好景，归去凤池夸。</p>",
        "avatarUrl": "https://pic1.zhimg.com/389a772ec26fa8910f8cea8867adef59_is.jpg",
        "name": "杭州",
        "url": "http://www.zhihu.com/api/v4/topics/19558830",
        "type": "topic",
        "excerpt": "东南形胜，三吴都会，钱塘自古繁华，烟柳画桥，风帘翠幕，参差十万人家。云树绕堤沙，怒涛卷霜雪，天堑无涯。市列珠玑，户盈罗绮，竞豪奢。 重湖叠巘清嘉。有三秋桂子，十里荷花。羌管弄晴，菱歌泛夜，嬉嬉钓叟莲娃。千骑拥高牙。乘醉听箫鼓，吟赏烟霞。异日图将好景，归去凤池夸。",
        "id": "19558830"
      }
    ],
    "badge": [],
    "includedAnswersCount": 0,
    "url": "http://www.zhihu.com/api/v4/people/ec1c55830f2868bed7c25b04c41530a1",
    "messageThreadToken": "9259154100",
    "logsCount": 11,
    "followingQuestionCount": 55,
    "thankedCount": 5,
    "gender": 1
  }
}

 */