package com.soul.app.models.res;

import java.util.List;

public class SocialMediaInfoBean {

    /**
     * data : {"url":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/v/t1.0-1/c0.2.50.50/p50x50/995659_414453772001071_161061694_n.jpg?oh=80f879e3dc09920c4a1d3fbc4c11e62b&oe=57B73F0A&__gda__=1468700930_639fc88f7d932092c88fd21b49321a73","is_silhouette":false}
     */

    private PictureEntity picture;
    /**
     * data : [{"picture":"https://fbcdn-photos-b-a.akamaihd.net/hphotos-ak-xla1/v/t1.0-0/s130x130/993574_889620194484424_7711759290854036986_n.jpg?oh=842a995ee28f0f2ab187b2c0d7454c8e&oe=578729AF&__gda__=1471794497_9f9060d00457ad95f5fb7474c3c1508b","id":"889620194484424"},{"picture":"https://fbcdn-photos-f-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-0/s130x130/12540952_889619967817780_4861450781185166051_n.jpg?oh=630b3dff8b4f5b439f8d82161555abf3&oe=57BCDC7B&__gda__=1468280907_f56f1794e45e039bf573316aec113db5","id":"889619967817780"},{"picture":"https://fbcdn-photos-e-a.akamaihd.net/hphotos-ak-xtl1/v/t1.0-0/p130x130/21530_935286309871749_3185845547377450295_n.jpg?oh=a5066af453b0ce4d1b187760c063b68f&oe=5781EFA7&__gda__=1467628583_2dd0cf29ff5e20696d924336da4bc354","id":"935286309871749"},{"picture":"https://fbcdn-photos-d-a.akamaihd.net/hphotos-ak-xft1/v/t1.0-0/s130x130/735217_933203646746682_5201592482735545652_n.jpg?oh=11533048ad23c8e694ad88097e0e7517&oe=5789CCD0&__gda__=1472067639_abafeea7e0c477ef408c4c1e67f25d64","id":"933203646746682"},{"picture":"https://fbcdn-photos-a-a.akamaihd.net/hphotos-ak-xat1/v/t1.0-0/s130x130/12239905_910514239015623_7565372725286277569_n.jpg?oh=a165d173356e8b6e9e12c175cce9498a&oe=578B62CA&__gda__=1468020406_f17a812945836b1dc75c27ca165dfc6b","id":"910514239015623"},{"picture":"https://fbcdn-photos-e-a.akamaihd.net/hphotos-ak-xat1/v/t1.0-0/s130x130/12243190_910514225682291_2582347823442380533_n.jpg?oh=bb9ff30e65e6f9fdd827978b8d64fd05&oe=5778F409&__gda__=1467531381_664ef2a43e36953a1a7b7e7b29a02a71","id":"910514225682291"}]
     * paging : {"cursors":{"after":"T1RFd05URTBNakkxTmpneU1qa3hPakUwTkRjeU1qYzBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD","before":"T0RnNU5qSXdNVGswTkRnME5ESTBPakUwTlRJM09EYzNOVEk2TXprME1EZAzVOalF3TmpRM09ETTIZD"},"next":"https://graph.facebook.com/v2.5/924383201008123/photos?access_token=EAAK9WBVZAGTIBAK1kWrZBeMyggsZA4arDrWZCdGHWnjK8AGXH72k4slGZChhHOKmoQDt21kdlzXIX07CHg1YezhtF23vJk2bTI08RoU2rUFK4PD1iyYUDogGkHKNDrvIfJD9aEZA4hASRHyPGH8hA24YZBuHqtTyHD2RfT7lnadp2eux8oHokAG&fields=id%2Cpicture&limit=6&after=T1RFd05URTBNakkxTmpneU1qa3hPakUwTkRjeU1qYzBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD"}
     */

    private PhotosEntity photos;
    /**
     * picture : {"data":{"url":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/v/t1.0-1/c0.2.50.50/p50x50/995659_414453772001071_161061694_n.jpg?oh=80f879e3dc09920c4a1d3fbc4c11e62b&oe=57B73F0A&__gda__=1468700930_639fc88f7d932092c88fd21b49321a73","is_silhouette":false}}
     * photos : {"data":[{"picture":"https://fbcdn-photos-b-a.akamaihd.net/hphotos-ak-xla1/v/t1.0-0/s130x130/993574_889620194484424_7711759290854036986_n.jpg?oh=842a995ee28f0f2ab187b2c0d7454c8e&oe=578729AF&__gda__=1471794497_9f9060d00457ad95f5fb7474c3c1508b","id":"889620194484424"},{"picture":"https://fbcdn-photos-f-a.akamaihd.net/hphotos-ak-xfp1/v/t1.0-0/s130x130/12540952_889619967817780_4861450781185166051_n.jpg?oh=630b3dff8b4f5b439f8d82161555abf3&oe=57BCDC7B&__gda__=1468280907_f56f1794e45e039bf573316aec113db5","id":"889619967817780"},{"picture":"https://fbcdn-photos-e-a.akamaihd.net/hphotos-ak-xtl1/v/t1.0-0/p130x130/21530_935286309871749_3185845547377450295_n.jpg?oh=a5066af453b0ce4d1b187760c063b68f&oe=5781EFA7&__gda__=1467628583_2dd0cf29ff5e20696d924336da4bc354","id":"935286309871749"},{"picture":"https://fbcdn-photos-d-a.akamaihd.net/hphotos-ak-xft1/v/t1.0-0/s130x130/735217_933203646746682_5201592482735545652_n.jpg?oh=11533048ad23c8e694ad88097e0e7517&oe=5789CCD0&__gda__=1472067639_abafeea7e0c477ef408c4c1e67f25d64","id":"933203646746682"},{"picture":"https://fbcdn-photos-a-a.akamaihd.net/hphotos-ak-xat1/v/t1.0-0/s130x130/12239905_910514239015623_7565372725286277569_n.jpg?oh=a165d173356e8b6e9e12c175cce9498a&oe=578B62CA&__gda__=1468020406_f17a812945836b1dc75c27ca165dfc6b","id":"910514239015623"},{"picture":"https://fbcdn-photos-e-a.akamaihd.net/hphotos-ak-xat1/v/t1.0-0/s130x130/12243190_910514225682291_2582347823442380533_n.jpg?oh=bb9ff30e65e6f9fdd827978b8d64fd05&oe=5778F409&__gda__=1467531381_664ef2a43e36953a1a7b7e7b29a02a71","id":"910514225682291"}],"paging":{"cursors":{"after":"T1RFd05URTBNakkxTmpneU1qa3hPakUwTkRjeU1qYzBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD","before":"T0RnNU5qSXdNVGswTkRnME5ESTBPakUwTlRJM09EYzNOVEk2TXprME1EZAzVOalF3TmpRM09ETTIZD"},"next":"https://graph.facebook.com/v2.5/924383201008123/photos?access_token=EAAK9WBVZAGTIBAK1kWrZBeMyggsZA4arDrWZCdGHWnjK8AGXH72k4slGZChhHOKmoQDt21kdlzXIX07CHg1YezhtF23vJk2bTI08RoU2rUFK4PD1iyYUDogGkHKNDrvIfJD9aEZA4hASRHyPGH8hA24YZBuHqtTyHD2RfT7lnadp2eux8oHokAG&fields=id%2Cpicture&limit=6&after=T1RFd05URTBNakkxTmpneU1qa3hPakUwTkRjeU1qYzBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD"}}
     * id : 924383201008123
     * first_name : Rinky
     * birthday : 01/07/1994
     * friends : {"summary":{"total_count":334},"data":[]}
     * email : rinks_sweetz@yahoo.in
     * likes : {"data":[{"created_time":"2016-02-12T10:37:58+0000","id":"1710096432546912","name":"MS Dhoni"},{"created_time":"2015-11-22T17:36:46+0000","id":"327339957364019","name":"Cine Cover"},{"created_time":"2015-11-07T03:13:29+0000","id":"119496081745956","name":"Ali Quli Mirza"},{"created_time":"2015-10-18T15:40:57+0000","id":"312206562323313","name":"Pyaar Ka Punchnama 2"},{"created_time":"2015-08-17T18:12:55+0000","id":"292280846039","name":"TechAhead Software"},{"created_time":"2015-08-03T16:21:42+0000","id":"168552943381","name":"Eros Now"},{"created_time":"2015-06-30T16:08:27+0000","id":"302984096428727","name":"Meenakshi Dutt Makeovers"},{"created_time":"2015-06-17T16:49:29+0000","id":"369613900572","name":"Nick Vujicic"},{"created_time":"2015-06-06T15:14:40+0000","id":"583629344989755","name":"Handmade Anything"},{"created_time":"2015-04-11T14:33:53+0000","id":"118306468218564","name":"Circle of Cricket - MS Dhoni"},{"created_time":"2015-04-03T15:50:26+0000","id":"1421998811435252","name":"IntelliLearn"},{"created_time":"2015-04-03T15:44:14+0000","id":"129073990494316","name":"Fresherslive"},{"created_time":"2015-02-13T16:09:42+0000","id":"506921692718847","name":"Jacqueline Fernandez"},{"created_time":"2015-02-06T05:02:53+0000","id":"136679869726300","name":"Gautam Gulati"},{"created_time":"2015-02-04T17:52:24+0000","id":"624485787563604","name":"Confluence, NIT Kurukshetra"},{"created_time":"2015-01-11T04:43:05+0000","id":"212270682131283","name":"ZaidAliT"},{"created_time":"2014-11-18T15:57:05+0000","id":"719122598157431","name":"Tiffin From Maa's Rasoi"},{"created_time":"2014-11-18T15:53:32+0000","id":"653726641406643","name":"Deepak Chaudhary"},{"created_time":"2014-10-26T05:30:17+0000","id":"350694251726008","name":"Rannvijay Singh Singha"},{"created_time":"2014-10-23T06:02:59+0000","id":"249880261833903","name":"Architecture & Design"},{"created_time":"2014-10-02T09:07:34+0000","id":"241382672702708","name":"Rakesh Upadhyay"},{"created_time":"2014-08-10T06:36:56+0000","id":"1457421414512687","name":"GL Bajaj Training & Placement Cell"},{"created_time":"2014-08-08T18:24:26+0000","id":"195750127230137","name":"ICHIEVE"},{"created_time":"2014-06-16T14:49:19+0000","id":"131720987018165","name":"Jhalak Dikhhla Jaa"},{"created_time":"2014-06-07T15:15:39+0000","id":"303966349687253","name":"Delhi Food Walks"},{"created_time":"2014-04-21T14:51:39+0000","id":"320561624692023","name":"2 States Movie"},{"created_time":"2014-03-10T09:28:19+0000","id":"684468061592499","name":"Parineeti Chopra Fan Club"},{"created_time":"2014-03-05T08:06:44+0000","id":"416591035061472","name":"McDonald's India"},{"created_time":"2014-02-23T21:14:33+0000","id":"714755485222673","name":"Mathew Thomas"},{"created_time":"2014-01-26T13:08:28+0000","id":"171250662896472","name":"Kullu Manali Heaven On Earth"},{"created_time":"2014-01-22T04:59:52+0000","id":"125944234111841","name":"Oriflame"},{"created_time":"2013-11-23T05:15:47+0000","id":"400156670087436","name":"Famous FACES of Glbitm"},{"created_time":"2013-11-18T15:43:05+0000","id":"422334831199973","name":"WebeleY Green Technologies"},{"created_time":"2013-10-30T14:51:40+0000","id":"408952632466793","name":"Innovative & Creative Cell (ICC)"},{"created_time":"2013-10-29T15:35:50+0000","id":"233720863460270","name":"Aditya Roy Kapoor Fans"},{"created_time":"2013-10-24T13:41:00+0000","id":"116732671827158","name":"Delhi for Women's Safety"},{"created_time":"2013-09-22T15:08:54+0000","id":"474874965856210","name":"Ranbir Kapoor Fan Club"},{"created_time":"2013-09-14T12:45:47+0000","id":"152591081480565","name":"Abhishek Gaurav Photography"},{"created_time":"2013-09-09T16:50:40+0000","id":"670490466309796","name":"Citizens for Accountable Governance"},{"created_time":"2013-09-01T10:52:49+0000","id":"323106474379169","name":"Technocrats"},{"created_time":"2013-08-23T08:29:11+0000","id":"70630972354","name":"Jesus Daily"},{"created_time":"2013-08-22T13:52:12+0000","id":"375294615893847","name":"Design & Fashion Blog"},{"created_time":"2013-08-18T10:34:24+0000","id":"277467002361228","name":"Technocrats Cs/It"},{"created_time":"2013-08-15T06:27:30+0000","id":"177145799133546","name":"Sushant Singh Rajput"},{"created_time":"2013-06-24T07:23:39+0000","id":"159997747513871","name":"The book joint"},{"created_time":"2013-05-04T05:33:01+0000","id":"491333290925295","name":"G.L. Bajaj Confesses and Proposes"},{"created_time":"2013-04-25T05:16:30+0000","id":"402297396533209","name":"Delhi Metro Confessions/Compliments"},{"created_time":"2013-03-28T05:52:48+0000","id":"144582695706543","name":"GLB Confessions"},{"created_time":"2013-03-26T12:41:06+0000","id":"430428147032690","name":"Ayushman Khurrana"},{"created_time":"2013-03-24T12:28:15+0000","id":"465659593501176","name":"G.L. BAJAJ Confessions"},{"created_time":"2013-03-24T04:40:29+0000","id":"177164422299684","name":"Golden Temple Amritsar"},{"created_time":"2013-01-25T16:48:48+0000","id":"416424281726834","name":"BeStEsT FrIeNdS FoR LiFe LoNg (BFFLL)"},{"created_time":"2013-01-15T13:33:43+0000","id":"168828186534773","name":"HAHK"},{"created_time":"2013-01-13T14:10:15+0000","id":"216818225015695","name":"General knowledge"},{"created_time":"2013-01-11T08:37:24+0000","id":"491155484258493","name":"Fashion"},{"created_time":"2013-01-06T14:11:33+0000","id":"240492292751313","name":"Damini"},{"created_time":"2013-01-04T09:13:13+0000","id":"193166437421816","name":"I Love Shoes, Bags & Boys"},{"created_time":"2012-11-18T12:09:03+0000","id":"125590214207152","name":"G. L. Bajaj Times"},{"created_time":"2012-11-12T13:31:39+0000","id":"161268897278333","name":"Student Of The Year"},{"created_time":"2012-10-27T12:59:30+0000","id":"390358940983405","name":"Shivin Narang"},{"created_time":"2012-07-24T09:40:21+0000","id":"141003102631868","name":"Shakti Mohan"},{"created_time":"2012-07-21T12:42:30+0000","id":"252094261486077","name":"G L Bajaj Institute of Technology & Management,Greater Noida."},{"created_time":"2012-06-17T14:07:36+0000","id":"298960010190516","name":"Timeline Updates"},{"created_time":"2012-05-28T08:11:27+0000","id":"410083815698042","name":"My Movie Community"},{"created_time":"2012-05-28T08:10:49+0000","id":"222104997886913","name":"80Apps"},{"created_time":"2012-05-19T17:06:30+0000","id":"458001127548287","name":"Which Avenger Are You?"},{"created_time":"2012-03-18T13:35:00+0000","id":"91190629059","name":"eBay India"},{"created_time":"2011-12-25T11:00:48+0000","id":"180434891992485","name":"EIT Digital Education"},{"created_time":"2011-12-25T11:00:27+0000","id":"38127943140","name":"Shoppers Stop"},{"created_time":"2011-12-25T11:00:18+0000","id":"114219621960016","name":"Samsung Mobile"},{"created_time":"2011-12-10T13:49:41+0000","id":"143141079033737","name":"Maybelline New York India"}],"paging":{"cursors":{"after":"MTQzMTQxMDc5MDMzNzM3","before":"MTcxMDA5NjQzMjU0NjkxMgZDZD"},"next":"https://graph.facebook.com/v2.5/924383201008123/likes?access_token=EAAK9WBVZAGTIBAK1kWrZBeMyggsZA4arDrWZCdGHWnjK8AGXH72k4slGZChhHOKmoQDt21kdlzXIX07CHg1YezhtF23vJk2bTI08RoU2rUFK4PD1iyYUDogGkHKNDrvIfJD9aEZA4hASRHyPGH8hA24YZBuHqtTyHD2RfT7lnadp2eux8oHokAG&limit=100&after=MTQzMTQxMDc5MDMzNzM3"}}
     * albums : {"data":[{"id":"825147697598341","created_time":"2015-08-24T16:17:14+0000","name":"Mobile Uploads"},{"id":"889619781151132","created_time":"2016-01-14T16:02:03+0000","name":"Untitled Album"},{"id":"176069265839524","created_time":"2012-02-20T04:42:57+0000","name":"Cover Photos"},{"id":"860806847365759","created_time":"2015-11-14T13:08:06+0000","name":"New Additions To The Family Of Friends :)"},{"id":"102224039890714","created_time":"2011-10-27T14:47:20+0000","name":"Profile Pictures"},{"id":"766050220174756","created_time":"2015-04-21T10:05:55+0000","name":"Untitled Album"},{"id":"710790222367423","created_time":"2015-01-10T06:36:04+0000","name":"21st BIRTHDAY <3 <3"},{"id":"513135992132848","created_time":"2014-01-09T15:15:41+0000","name":"A Perfect 20 <3"},{"id":"350250958421353","created_time":"2013-02-03T15:09:35+0000","name":"Happy Birthday Ajay"},{"id":"334015543378228","created_time":"2013-01-07T12:30:48+0000","name":"19 i am :P"},{"id":"209613212485129","created_time":"2012-04-13T14:29:53+0000","name":"Timeline Photos"},{"id":"186114211501696","created_time":"2012-03-06T18:01:30+0000","name":":) :) ........."},{"id":"150790938367357","created_time":"2012-01-15T16:49:16+0000","name":"Untitled Album"},{"id":"136429583136826","created_time":"2011-12-22T14:53:06+0000","name":"Frnzzz"}],"paging":{"cursors":{"after":"MTM2NDI5NTgzMTM2ODI2","before":"ODI1MTQ3Njk3NTk4MzQx"}}}
     * name : Rinky Singh
     * last_name : Singh
     * gender : female
     */

    private String id;
    private String first_name;
    private String birthday;
    /**
     * summary : {"total_count":334}
     * data : []
     */

    private FriendsEntity friends;
    private String email;
    /**
     * data : [{"created_time":"2016-02-12T10:37:58+0000","id":"1710096432546912","name":"MS Dhoni"},{"created_time":"2015-11-22T17:36:46+0000","id":"327339957364019","name":"Cine Cover"},{"created_time":"2015-11-07T03:13:29+0000","id":"119496081745956","name":"Ali Quli Mirza"},{"created_time":"2015-10-18T15:40:57+0000","id":"312206562323313","name":"Pyaar Ka Punchnama 2"},{"created_time":"2015-08-17T18:12:55+0000","id":"292280846039","name":"TechAhead Software"},{"created_time":"2015-08-03T16:21:42+0000","id":"168552943381","name":"Eros Now"},{"created_time":"2015-06-30T16:08:27+0000","id":"302984096428727","name":"Meenakshi Dutt Makeovers"},{"created_time":"2015-06-17T16:49:29+0000","id":"369613900572","name":"Nick Vujicic"},{"created_time":"2015-06-06T15:14:40+0000","id":"583629344989755","name":"Handmade Anything"},{"created_time":"2015-04-11T14:33:53+0000","id":"118306468218564","name":"Circle of Cricket - MS Dhoni"},{"created_time":"2015-04-03T15:50:26+0000","id":"1421998811435252","name":"IntelliLearn"},{"created_time":"2015-04-03T15:44:14+0000","id":"129073990494316","name":"Fresherslive"},{"created_time":"2015-02-13T16:09:42+0000","id":"506921692718847","name":"Jacqueline Fernandez"},{"created_time":"2015-02-06T05:02:53+0000","id":"136679869726300","name":"Gautam Gulati"},{"created_time":"2015-02-04T17:52:24+0000","id":"624485787563604","name":"Confluence, NIT Kurukshetra"},{"created_time":"2015-01-11T04:43:05+0000","id":"212270682131283","name":"ZaidAliT"},{"created_time":"2014-11-18T15:57:05+0000","id":"719122598157431","name":"Tiffin From Maa's Rasoi"},{"created_time":"2014-11-18T15:53:32+0000","id":"653726641406643","name":"Deepak Chaudhary"},{"created_time":"2014-10-26T05:30:17+0000","id":"350694251726008","name":"Rannvijay Singh Singha"},{"created_time":"2014-10-23T06:02:59+0000","id":"249880261833903","name":"Architecture & Design"},{"created_time":"2014-10-02T09:07:34+0000","id":"241382672702708","name":"Rakesh Upadhyay"},{"created_time":"2014-08-10T06:36:56+0000","id":"1457421414512687","name":"GL Bajaj Training & Placement Cell"},{"created_time":"2014-08-08T18:24:26+0000","id":"195750127230137","name":"ICHIEVE"},{"created_time":"2014-06-16T14:49:19+0000","id":"131720987018165","name":"Jhalak Dikhhla Jaa"},{"created_time":"2014-06-07T15:15:39+0000","id":"303966349687253","name":"Delhi Food Walks"},{"created_time":"2014-04-21T14:51:39+0000","id":"320561624692023","name":"2 States Movie"},{"created_time":"2014-03-10T09:28:19+0000","id":"684468061592499","name":"Parineeti Chopra Fan Club"},{"created_time":"2014-03-05T08:06:44+0000","id":"416591035061472","name":"McDonald's India"},{"created_time":"2014-02-23T21:14:33+0000","id":"714755485222673","name":"Mathew Thomas"},{"created_time":"2014-01-26T13:08:28+0000","id":"171250662896472","name":"Kullu Manali Heaven On Earth"},{"created_time":"2014-01-22T04:59:52+0000","id":"125944234111841","name":"Oriflame"},{"created_time":"2013-11-23T05:15:47+0000","id":"400156670087436","name":"Famous FACES of Glbitm"},{"created_time":"2013-11-18T15:43:05+0000","id":"422334831199973","name":"WebeleY Green Technologies"},{"created_time":"2013-10-30T14:51:40+0000","id":"408952632466793","name":"Innovative & Creative Cell (ICC)"},{"created_time":"2013-10-29T15:35:50+0000","id":"233720863460270","name":"Aditya Roy Kapoor Fans"},{"created_time":"2013-10-24T13:41:00+0000","id":"116732671827158","name":"Delhi for Women's Safety"},{"created_time":"2013-09-22T15:08:54+0000","id":"474874965856210","name":"Ranbir Kapoor Fan Club"},{"created_time":"2013-09-14T12:45:47+0000","id":"152591081480565","name":"Abhishek Gaurav Photography"},{"created_time":"2013-09-09T16:50:40+0000","id":"670490466309796","name":"Citizens for Accountable Governance"},{"created_time":"2013-09-01T10:52:49+0000","id":"323106474379169","name":"Technocrats"},{"created_time":"2013-08-23T08:29:11+0000","id":"70630972354","name":"Jesus Daily"},{"created_time":"2013-08-22T13:52:12+0000","id":"375294615893847","name":"Design & Fashion Blog"},{"created_time":"2013-08-18T10:34:24+0000","id":"277467002361228","name":"Technocrats Cs/It"},{"created_time":"2013-08-15T06:27:30+0000","id":"177145799133546","name":"Sushant Singh Rajput"},{"created_time":"2013-06-24T07:23:39+0000","id":"159997747513871","name":"The book joint"},{"created_time":"2013-05-04T05:33:01+0000","id":"491333290925295","name":"G.L. Bajaj Confesses and Proposes"},{"created_time":"2013-04-25T05:16:30+0000","id":"402297396533209","name":"Delhi Metro Confessions/Compliments"},{"created_time":"2013-03-28T05:52:48+0000","id":"144582695706543","name":"GLB Confessions"},{"created_time":"2013-03-26T12:41:06+0000","id":"430428147032690","name":"Ayushman Khurrana"},{"created_time":"2013-03-24T12:28:15+0000","id":"465659593501176","name":"G.L. BAJAJ Confessions"},{"created_time":"2013-03-24T04:40:29+0000","id":"177164422299684","name":"Golden Temple Amritsar"},{"created_time":"2013-01-25T16:48:48+0000","id":"416424281726834","name":"BeStEsT FrIeNdS FoR LiFe LoNg (BFFLL)"},{"created_time":"2013-01-15T13:33:43+0000","id":"168828186534773","name":"HAHK"},{"created_time":"2013-01-13T14:10:15+0000","id":"216818225015695","name":"General knowledge"},{"created_time":"2013-01-11T08:37:24+0000","id":"491155484258493","name":"Fashion"},{"created_time":"2013-01-06T14:11:33+0000","id":"240492292751313","name":"Damini"},{"created_time":"2013-01-04T09:13:13+0000","id":"193166437421816","name":"I Love Shoes, Bags & Boys"},{"created_time":"2012-11-18T12:09:03+0000","id":"125590214207152","name":"G. L. Bajaj Times"},{"created_time":"2012-11-12T13:31:39+0000","id":"161268897278333","name":"Student Of The Year"},{"created_time":"2012-10-27T12:59:30+0000","id":"390358940983405","name":"Shivin Narang"},{"created_time":"2012-07-24T09:40:21+0000","id":"141003102631868","name":"Shakti Mohan"},{"created_time":"2012-07-21T12:42:30+0000","id":"252094261486077","name":"G L Bajaj Institute of Technology & Management,Greater Noida."},{"created_time":"2012-06-17T14:07:36+0000","id":"298960010190516","name":"Timeline Updates"},{"created_time":"2012-05-28T08:11:27+0000","id":"410083815698042","name":"My Movie Community"},{"created_time":"2012-05-28T08:10:49+0000","id":"222104997886913","name":"80Apps"},{"created_time":"2012-05-19T17:06:30+0000","id":"458001127548287","name":"Which Avenger Are You?"},{"created_time":"2012-03-18T13:35:00+0000","id":"91190629059","name":"eBay India"},{"created_time":"2011-12-25T11:00:48+0000","id":"180434891992485","name":"EIT Digital Education"},{"created_time":"2011-12-25T11:00:27+0000","id":"38127943140","name":"Shoppers Stop"},{"created_time":"2011-12-25T11:00:18+0000","id":"114219621960016","name":"Samsung Mobile"},{"created_time":"2011-12-10T13:49:41+0000","id":"143141079033737","name":"Maybelline New York India"}]
     * paging : {"cursors":{"after":"MTQzMTQxMDc5MDMzNzM3","before":"MTcxMDA5NjQzMjU0NjkxMgZDZD"},"next":"https://graph.facebook.com/v2.5/924383201008123/likes?access_token=EAAK9WBVZAGTIBAK1kWrZBeMyggsZA4arDrWZCdGHWnjK8AGXH72k4slGZChhHOKmoQDt21kdlzXIX07CHg1YezhtF23vJk2bTI08RoU2rUFK4PD1iyYUDogGkHKNDrvIfJD9aEZA4hASRHyPGH8hA24YZBuHqtTyHD2RfT7lnadp2eux8oHokAG&limit=100&after=MTQzMTQxMDc5MDMzNzM3"}
     */

    private LikesEntity likes;
    /**
     * data : [{"id":"825147697598341","created_time":"2015-08-24T16:17:14+0000","name":"Mobile Uploads"},{"id":"889619781151132","created_time":"2016-01-14T16:02:03+0000","name":"Untitled Album"},{"id":"176069265839524","created_time":"2012-02-20T04:42:57+0000","name":"Cover Photos"},{"id":"860806847365759","created_time":"2015-11-14T13:08:06+0000","name":"New Additions To The Family Of Friends :)"},{"id":"102224039890714","created_time":"2011-10-27T14:47:20+0000","name":"Profile Pictures"},{"id":"766050220174756","created_time":"2015-04-21T10:05:55+0000","name":"Untitled Album"},{"id":"710790222367423","created_time":"2015-01-10T06:36:04+0000","name":"21st BIRTHDAY <3 <3"},{"id":"513135992132848","created_time":"2014-01-09T15:15:41+0000","name":"A Perfect 20 <3"},{"id":"350250958421353","created_time":"2013-02-03T15:09:35+0000","name":"Happy Birthday Ajay"},{"id":"334015543378228","created_time":"2013-01-07T12:30:48+0000","name":"19 i am :P"},{"id":"209613212485129","created_time":"2012-04-13T14:29:53+0000","name":"Timeline Photos"},{"id":"186114211501696","created_time":"2012-03-06T18:01:30+0000","name":":) :) ........."},{"id":"150790938367357","created_time":"2012-01-15T16:49:16+0000","name":"Untitled Album"},{"id":"136429583136826","created_time":"2011-12-22T14:53:06+0000","name":"Frnzzz"}]
     * paging : {"cursors":{"after":"MTM2NDI5NTgzMTM2ODI2","before":"ODI1MTQ3Njk3NTk4MzQx"}}
     */

    private AlbumsEntity albums;
    private String name;
    private String last_name;
    private String gender;
    private HometownBean hometown;

    public HometownBean getHometown() {
        return hometown;
    }

    public void setHometown(HometownBean hometown) {
        this.hometown = hometown;
    }

    /**
     * id : 140529543050645
     * position : {"id":"145329302146447","name":"Android Application Developer"}
     * employer : {"id":"292280846039","name":"TechAhead Software"}
     * start_date : 0000-00
     * location : {"id":"130646063637019","name":"Noida, India"}
     */

    private List<WorkBean> work;
    /**
     * id : 140530119717254
     * type : High School
     * school : {"id":"593829244012805","name":"High School Maheshwari Academy +2,Katihar"}
     */

    private List<EducationBean> education;

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public void setPhotos(PhotosEntity photos) {
        this.photos = photos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setFriends(FriendsEntity friends) {
        this.friends = friends;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLikes(LikesEntity likes) {
        this.likes = likes;
    }

    public void setAlbums(AlbumsEntity albums) {
        this.albums = albums;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public PhotosEntity getPhotos() {
        return photos;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public FriendsEntity getFriends() {
        return friends;
    }

    public String getEmail() {
        return email;
    }

    public LikesEntity getLikes() {
        return likes;
    }

    public AlbumsEntity getAlbums() {
        return albums;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public List<WorkBean> getWork() {
        return work;
    }

    public void setWork(List<WorkBean> work) {
        this.work = work;
    }

    public List<EducationBean> getEducation() {
        return education;
    }

    public void setEducation(List<EducationBean> education) {
        this.education = education;
    }

    public static class HometownBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public static class PictureEntity {
        /**
         * url : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/v/t1.0-1/c0.2.50.50/p50x50/995659_414453772001071_161061694_n.jpg?oh=80f879e3dc09920c4a1d3fbc4c11e62b&oe=57B73F0A&__gda__=1468700930_639fc88f7d932092c88fd21b49321a73
         * is_silhouette : false
         */

        private DataEntity data;

        public void setData(DataEntity data) {
            this.data = data;
        }

        public DataEntity getData() {
            return data;
        }

        public static class DataEntity {
            private String url;
            private boolean is_silhouette;

            public void setUrl(String url) {
                this.url = url;
            }

            public void setIs_silhouette(boolean is_silhouette) {
                this.is_silhouette = is_silhouette;
            }

            public String getUrl() {
                return url;
            }

            public boolean isIs_silhouette() {
                return is_silhouette;
            }
        }
    }

    public static class PhotosEntity {
        /**
         * cursors : {"after":"T1RFd05URTBNakkxTmpneU1qa3hPakUwTkRjeU1qYzBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD","before":"T0RnNU5qSXdNVGswTkRnME5ESTBPakUwTlRJM09EYzNOVEk2TXprME1EZAzVOalF3TmpRM09ETTIZD"}
         * next : https://graph.facebook.com/v2.5/924383201008123/photos?access_token=EAAK9WBVZAGTIBAK1kWrZBeMyggsZA4arDrWZCdGHWnjK8AGXH72k4slGZChhHOKmoQDt21kdlzXIX07CHg1YezhtF23vJk2bTI08RoU2rUFK4PD1iyYUDogGkHKNDrvIfJD9aEZA4hASRHyPGH8hA24YZBuHqtTyHD2RfT7lnadp2eux8oHokAG&fields=id%2Cpicture&limit=6&after=T1RFd05URTBNakkxTmpneU1qa3hPakUwTkRjeU1qYzBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD
         */

        private PagingEntity paging;
        /**
         * picture : https://fbcdn-photos-b-a.akamaihd.net/hphotos-ak-xla1/v/t1.0-0/s130x130/993574_889620194484424_7711759290854036986_n.jpg?oh=842a995ee28f0f2ab187b2c0d7454c8e&oe=578729AF&__gda__=1471794497_9f9060d00457ad95f5fb7474c3c1508b
         * id : 889620194484424
         */

        private List<DataEntity> data;

        public void setPaging(PagingEntity paging) {
            this.paging = paging;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public PagingEntity getPaging() {
            return paging;
        }

        public List<DataEntity> getData() {
            return data;
        }

        public static class PagingEntity {
            /**
             * after : T1RFd05URTBNakkxTmpneU1qa3hPakUwTkRjeU1qYzBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD
             * before : T0RnNU5qSXdNVGswTkRnME5ESTBPakUwTlRJM09EYzNOVEk2TXprME1EZAzVOalF3TmpRM09ETTIZD
             */

            private CursorsEntity cursors;
            private String next;

            public void setCursors(CursorsEntity cursors) {
                this.cursors = cursors;
            }

            public void setNext(String next) {
                this.next = next;
            }

            public CursorsEntity getCursors() {
                return cursors;
            }

            public String getNext() {
                return next;
            }

            public static class CursorsEntity {
                private String after;
                private String before;

                public void setAfter(String after) {
                    this.after = after;
                }

                public void setBefore(String before) {
                    this.before = before;
                }

                public String getAfter() {
                    return after;
                }

                public String getBefore() {
                    return before;
                }
            }
        }

        public static class DataEntity {
            private String picture;
            private String id;

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicture() {
                return picture;
            }

            public String getId() {
                return id;
            }
        }
    }

    public static class FriendsEntity {
        /**
         * total_count : 334
         */

        private SummaryEntity summary;
        private List<?> data;

        public void setSummary(SummaryEntity summary) {
            this.summary = summary;
        }

        public void setData(List<?> data) {
            this.data = data;
        }

        public SummaryEntity getSummary() {
            return summary;
        }

        public List<?> getData() {
            return data;
        }

        public static class SummaryEntity {
            private int total_count;

            public void setTotal_count(int total_count) {
                this.total_count = total_count;
            }

            public int getTotal_count() {
                return total_count;
            }
        }
    }

    public static class LikesEntity {
        /**
         * cursors : {"after":"MTQzMTQxMDc5MDMzNzM3","before":"MTcxMDA5NjQzMjU0NjkxMgZDZD"}
         * next : https://graph.facebook.com/v2.5/924383201008123/likes?access_token=EAAK9WBVZAGTIBAK1kWrZBeMyggsZA4arDrWZCdGHWnjK8AGXH72k4slGZChhHOKmoQDt21kdlzXIX07CHg1YezhtF23vJk2bTI08RoU2rUFK4PD1iyYUDogGkHKNDrvIfJD9aEZA4hASRHyPGH8hA24YZBuHqtTyHD2RfT7lnadp2eux8oHokAG&limit=100&after=MTQzMTQxMDc5MDMzNzM3
         */

        private PagingEntity paging;
        /**
         * created_time : 2016-02-12T10:37:58+0000
         * id : 1710096432546912
         * name : MS Dhoni
         */

        private List<DataEntity> data;

        public void setPaging(PagingEntity paging) {
            this.paging = paging;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public PagingEntity getPaging() {
            return paging;
        }

        public List<DataEntity> getData() {
            return data;
        }

        public static class PagingEntity {
            /**
             * after : MTQzMTQxMDc5MDMzNzM3
             * before : MTcxMDA5NjQzMjU0NjkxMgZDZD
             */

            private CursorsEntity cursors;
            private String next;

            public void setCursors(CursorsEntity cursors) {
                this.cursors = cursors;
            }

            public void setNext(String next) {
                this.next = next;
            }

            public CursorsEntity getCursors() {
                return cursors;
            }

            public String getNext() {
                return next;
            }

            public static class CursorsEntity {
                private String after;
                private String before;

                public void setAfter(String after) {
                    this.after = after;
                }

                public void setBefore(String before) {
                    this.before = before;
                }

                public String getAfter() {
                    return after;
                }

                public String getBefore() {
                    return before;
                }
            }
        }

        public static class DataEntity {
            private String created_time;
            private String id;
            private String name;

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreated_time() {
                return created_time;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }


            @Override
            public String toString() {
                return name;
            }
        }

    }

    public static class AlbumsEntity {
        /**
         * cursors : {"after":"MTM2NDI5NTgzMTM2ODI2","before":"ODI1MTQ3Njk3NTk4MzQx"}
         */

        private PagingEntity paging;
        /**
         * id : 825147697598341
         * created_time : 2015-08-24T16:17:14+0000
         * name : Mobile Uploads
         */

        private List<DataEntity> data;

        public void setPaging(PagingEntity paging) {
            this.paging = paging;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public PagingEntity getPaging() {
            return paging;
        }

        public List<DataEntity> getData() {
            return data;
        }

        public static class PagingEntity {
            /**
             * after : MTM2NDI5NTgzMTM2ODI2
             * before : ODI1MTQ3Njk3NTk4MzQx
             */

            private CursorsEntity cursors;

            public void setCursors(CursorsEntity cursors) {
                this.cursors = cursors;
            }

            public CursorsEntity getCursors() {
                return cursors;
            }

            public static class CursorsEntity {
                private String after;
                private String before;

                public void setAfter(String after) {
                    this.after = after;
                }

                public void setBefore(String before) {
                    this.before = before;
                }

                public String getAfter() {
                    return after;
                }

                public String getBefore() {
                    return before;
                }
            }
        }

        public static class DataEntity {
            private String id;
            private String created_time;
            private String name;

            public void setId(String id) {
                this.id = id;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public String getCreated_time() {
                return created_time;
            }

            public String getName() {
                return name;
            }
        }
    }

    public static class WorkBean {
        private String id;
        /**
         * id : 145329302146447
         * name : Android Application Developer
         */

        private PositionBean position;
        /**
         * id : 292280846039
         * name : TechAhead Software
         */

        private EmployerBean employer;
        private String start_date;
        /**
         * id : 130646063637019
         * name : Noida, India
         */

        private LocationBean location;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public PositionBean getPosition() {
            return position;
        }

        public void setPosition(PositionBean position) {
            this.position = position;
        }

        public EmployerBean getEmployer() {
            return employer;
        }

        public void setEmployer(EmployerBean employer) {
            this.employer = employer;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public static class PositionBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class EmployerBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class LocationBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class EducationBean {
        private String id;
        private String type;
        /**
         * id : 593829244012805
         * name : High School Maheshwari Academy +2,Katihar
         */

        private SchoolBean school;
        /**
         * id : 128909403846469
         * name : MCA
         */

        private DegreeBean degree;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public DegreeBean getDegree() {
            return degree;
        }

        public void setDegree(DegreeBean degree) {
            this.degree = degree;
        }

        public static class SchoolBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class DegreeBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
