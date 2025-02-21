package flight_booking.demo.domain.flight.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Airport {
	ICN("인천 국제공항"),
	GMP("김포 국제공항"),
	CJU("제주 국제공항"),
	PUS("김해 국제공항"),
	TAE("대구 국제공항"),
	CJJ("청주 국제공항"),
	RSU("여수 공항"),
	KWJ("광주 공항"),
	USN("울산 공항"),
	KPO("포항 공항"),
	HIN("사천 공항"),
	WJU("원주 공항"),
	YNY("양양 국제공항"),
	NRT("나리타 국제공항"),
	HND("하네다 국제공항"),
	KIX("간사이 국제공항"),
	FUK("후쿠오카 공항"),
	OKA("나하 공항"),
	NGO("나고야 국제공항"),
	JFK("존 F. 케네디 국제공항"),
	LAX("로스앤젤레스 국제공항"),
	SFO("샌프란시스코 국제공항"),
	ORD("오헤어 국제공항"),
	LHR("히드로 공항"),
	CDG("샤를 드골 공항"),
	FRA("프랑크푸르트 국제공항"),
	SIN("창이 국제공항"),
	HKG("홍콩 국제공항"),
	BKK("수완나품 국제공항");

	private final String officialName;

}