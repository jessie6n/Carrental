package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// JWT 登入用：驗證帳號密碼
	Member findByIdNumberAndPassword(String username, String password);

	Member findByEmailAndPassword(String username, String password);

	// 註冊檢查用
	Member findByEmail(String email);

	Member findByPhone(String phone);

	Member findByIdNumber(String idnumber);

	// 查詢特定會員
	Member findByMemberId(String memid);

	// 自定義 SQL：產生流水號 (e.g. 取得目前最大ID)
	@Query(value = "select member_id from member order by id desc limit 1", nativeQuery = true)
	String queryNextMemberId();

	// 員工後台：多條件模糊搜尋會員
	@Query(value = "SELECT * FROM member " + "WHERE (:name IS NULL OR name LIKE CONCAT('%', :name, '%')) "
			+ "  AND (:id_number IS NULL OR id_number = :id_number) " + "  AND (:phone IS NULL OR phone = :phone) "
			+ "  AND (:member_id IS NULL OR member_id = :member_id)", nativeQuery = true)
	List<Member> search(@Param("name") String membername, @Param("id_number") String idnumber,
			@Param("phone") String phone, @Param("member_id") String memberid);
}