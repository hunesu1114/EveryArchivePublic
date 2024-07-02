package project.everyarchive.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.everyarchive.dto.MemoDto;
import project.everyarchive.entity.Member;
import project.everyarchive.entity.Memo;
import project.everyarchive.repository.MemoRepository;
import project.everyarchive.repository.QMemoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final QMemoRepository qMemoRepository;

    @Transactional
    public void save(MemoDto dto, HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute("member");
        Memo memo = Memo.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();
        memoRepository.save(memo);
    }


    public List<MemoDto> findAllByMemberIdDesc(HttpServletRequest request) {
        List<MemoDto> responseList = new ArrayList<>();
        Long sessionMemberId = ((Member) request.getSession().getAttribute("member")).getId();
        List<Memo> entities = qMemoRepository.findAllByMemberIdDesc(sessionMemberId);
        for (Memo entity : entities) {
            MemoDto memoDto = new MemoDto();
            memoDto.setId(entity.getId());
            memoDto.setTitle(entity.getTitle());
            memoDto.setContent(entity.getContent());
            responseList.add(memoDto);
        }
        return responseList;
    }

    @Transactional
    public void updateMemo(MemoDto dto) {
        Memo memo = memoRepository.findById(dto.getId()).orElseThrow();
        memo.updateMemo(dto.getTitle(), dto.getContent());
    }

    @Transactional
    public void deleteMemo(Long id) {
        memoRepository.deleteById(id);
    }
}
