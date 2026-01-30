package beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import api.core.BDeConfig;
import api.text.LuceneIndexService;

@ManagedBean(eager = true)
@ApplicationScoped
public class LuceneInitializer  {

    @PostConstruct
    public void init() {
        try {
			new LuceneIndexService(BDeConfig.getInstance()).buildIndex();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

