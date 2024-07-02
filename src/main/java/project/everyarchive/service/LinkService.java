package project.everyarchive.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.everyarchive.dto.LinkDto;
import project.everyarchive.entity.Link;
import project.everyarchive.entity.Member;
import project.everyarchive.repository.LinkRepository;
import project.everyarchive.repository.QLinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final QLinkRepository qLinkRepository;

    @Transactional
    public void saveLink(LinkDto dto, HttpServletRequest request) {
        Link link = Link.builder()
                .category(dto.getCategory())
                .description(dto.getDescription())
                .url(dto.getUrl())
                .member((Member) request.getSession().getAttribute("member"))
                .build();
        linkRepository.save(link);
    }

    @Transactional
    public void updateLink(Long linkId, LinkDto dto, HttpServletRequest request) {
        Long sessionMemberId = ((Member) request.getSession().getAttribute("member")).getId();
        Link link = linkRepository.findById(linkId).orElseThrow();
        if (link.getMember().getId() == sessionMemberId) {
            link.updateLinkInfo(dto);
        } else {
            throw new PermissionDeniedDataAccessException("permission denied",new Exception());
        }
    }

    public List<LinkDto> getLinks(HttpServletRequest request) {
        List<LinkDto> responseList = new ArrayList<>();
        Long sessionMemberId = ((Member) request.getSession().getAttribute("member")).getId();
        List<Link> entities = qLinkRepository.findAllByMemberIdDesc(sessionMemberId);
        for (Link entity : entities) {
            LinkDto linkDto = new LinkDto();
            linkDto.setCategory(entity.getCategory());
            linkDto.setUrl(entity.getUrl());
            linkDto.setDescription(entity.getDescription());
            linkDto.setId(entity.getId());
            responseList.add(linkDto);
        }
        return responseList;
    }

    public LinkDto getLink(Long linkId) {
        Link entity = qLinkRepository.findOneByDescription(linkId);
        LinkDto linkDto = new LinkDto();
        linkDto.setCategory(entity.getCategory());
        linkDto.setUrl(entity.getUrl());
        linkDto.setDescription(entity.getDescription());
        linkDto.setId(entity.getId());
        return linkDto;
    }

    @Transactional
    public void deleteLink(Long id, HttpServletRequest request) {
        Long sessionMemberId = ((Member) request.getSession().getAttribute("member")).getId();
        Link link = linkRepository.findById(id).orElseThrow();
        if (link.getMember().getId() == sessionMemberId) {
            linkRepository.deleteById(id);
        } else {
            throw new PermissionDeniedDataAccessException("permission denied",new Exception());
        }
    }

}
