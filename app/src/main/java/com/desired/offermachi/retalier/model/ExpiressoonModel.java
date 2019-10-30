package com.desired.offermachi.retalier.model;

public class ExpiressoonModel {
    private String id,Offerid,Offername,OfferCategory,OfferSubCategory,Offertype,Offertypename,Offervalue,OfferDetail,Offerstartdate,
            Offerenddate,Offertime,Offerdescription,Coupon_code,Posted_by,Offerstatus,Offerbrandname,DealsStatus,OfferImage,Offerqrcodeimage,coupon_code_status,shop_logo,offer_title_slug;


    public ExpiressoonModel(String id, String offer_id, String offer_title, String offer_category, String sub_category, String offer_type, String offer_type_name, String offer_value, String offer_details, String start_date, String end_date, String alltime, String description, String coupon_code, String posted_by, String status, String offer_brand_name, String favourite_status, String offer_image, String qr_code_image, String coupon_code_status, String shop_logo, String offer_title_slug) {
        this.id = id;
        this.Offerid = offer_id;
        this.Offername = offer_title;
        this.OfferCategory=offer_category;
        this.OfferSubCategory=sub_category;
        this.Offertype=offer_type;
        this.Offertypename=offer_type_name;
        this.Offervalue=offer_value;
        this.OfferDetail=offer_details;
        this.Offerstartdate=start_date;
        this.Offerenddate=end_date;
        this.Offertime=alltime;
        this.Offerdescription=description;
        this.Coupon_code=coupon_code;
        this.Posted_by=posted_by;
        this.Offerstatus=status;
        this.Offerbrandname=offer_brand_name;
        this.DealsStatus=favourite_status;
        this.OfferImage=offer_image;
        this.Offerqrcodeimage=qr_code_image;
        this.coupon_code_status=coupon_code_status;
        this.shop_logo=shop_logo;
        this.offer_title_slug=offer_title_slug;

    }

    public ExpiressoonModel(String id, String offer_id, String offer_title, String offer_category, String sub_category, String offer_type, String offer_type_name, String offer_value, String offer_details, String start_date, String end_date, String alltime, String description, String coupon_code, String posted_by, String status, String offer_brand_name, String offer_image, String shop_logo) {
        this.id = id;
        this.Offerid = offer_id;
        this.Offername = offer_title;
        this.OfferCategory=offer_category;
        this.OfferSubCategory=sub_category;
        this.Offertype=offer_type;
        this.Offertypename=offer_type_name;
        this.Offervalue=offer_value;
        this.OfferDetail=offer_details;
        this.Offerstartdate=start_date;
        this.Offerenddate=end_date;
        this.Offertime=alltime;
        this.Offerdescription=description;
        this.Coupon_code=coupon_code;
        this.Posted_by=posted_by;
        this.Offerstatus=status;
        this.Offerbrandname=offer_brand_name;
        this.OfferImage=offer_image;
        this.shop_logo=shop_logo;

    }

    public String getId() {
        return id;
    }

    public String getOfferid() {
        return Offerid;
    }

    public String getOffername() {
        return Offername;
    }

    public String getOfferCategory() {
        return OfferCategory;
    }

    public String getOfferSubCategory() {
        return OfferSubCategory;
    }

    public String getOffertype() {
        return Offertype;
    }

    public String getOffervalue() {
        return Offervalue;
    }

    public String getOfferDetail() {
        return OfferDetail;
    }


    public String getOfferImage() {
        return OfferImage;
    }

    public String getOfferstartdate() {
        return Offerstartdate;
    }

    public String getOfferenddate() {
        return Offerenddate;
    }

    public String getOffertime() {
        return Offertime;
    }

    public String getOfferdescription() {
        return Offerdescription;
    }

    public String getCoupon_code() {
        return Coupon_code;
    }

    public String getPosted_by() {
        return Posted_by;
    }

    public String getOfferstatus() {
        return Offerstatus;
    }

    public String getOfferqrcodeimage() {
        return Offerqrcodeimage;
    }

    public String getOffertypename() {
        return Offertypename;
    }

    public String getOfferbrandname() {
        return Offerbrandname;
    }

    public String getDealsStatus() {
        return DealsStatus;
    }
}
